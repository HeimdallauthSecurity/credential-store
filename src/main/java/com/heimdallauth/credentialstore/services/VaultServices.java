package com.heimdallauth.credentialstore.services;

import com.heimdallauth.credentialstore.dto.CredentialValidationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.vault.support.Plaintext;

@Service
@Slf4j
public class VaultServices {
    private final VaultTemplate vaultTemplate;
    private final VaultTransitOperations commonTransitOperations;
    private final VaultTransitOperations credentialTransitOperations;
    private static final String TRANSPORT_KEY = "credentials-transport-key";
    private static final String CREDENTIAL_KEY = "credential-encryption-key";
    private static final String CREDENTIAL_RESPONSE_TRANSPORT_KEY = "credential-response-transport-key";

    @Autowired
    public VaultServices(VaultTemplate vaultTemplate) {
        this.vaultTemplate = vaultTemplate;
        this.commonTransitOperations = vaultTemplate.opsForTransit("transit/heimdallauth/common");
        this.credentialTransitOperations = vaultTemplate.opsForTransit("transit/heimdallauth/credential-store");
    }
    public String hashPassword(String unencryptedPassword){
        return credentialTransitOperations.getHmac(CREDENTIAL_KEY, Plaintext.of(unencryptedPassword)).getHmac();
    }
    public Boolean isPasswordValid(String storedHmac, String unencryptedPassword) {
        String providedHashedPassword = this.hashPassword(unencryptedPassword);
        return storedHmac.equals(providedHashedPassword);
    }
    public String encryptUsingCredentialStoreKey(String hashedPassword){
        return credentialTransitOperations.encrypt(CREDENTIAL_KEY, Plaintext.of(hashedPassword)).getCiphertext();
    }
    public String decryptUsingCredentialStoreKey(String encryptedPassword){
        return credentialTransitOperations.decrypt(CREDENTIAL_KEY, encryptedPassword);
    }

    public String decryptFromTransit(String encryptedPassword) {
        return commonTransitOperations.decrypt(TRANSPORT_KEY, encryptedPassword);
    }
    public String encryptUsingCommonKey(CredentialValidationResponse responsePayload){
        return commonTransitOperations.encrypt(CREDENTIAL_RESPONSE_TRANSPORT_KEY, Plaintext.of(responsePayload.toString())).getCiphertext();
    }
}

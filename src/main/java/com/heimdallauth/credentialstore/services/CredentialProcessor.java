package com.heimdallauth.credentialstore.services;

import com.heimdallauth.credentialstore.dao.CredentialsDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialProcessor {
    private final CredentialsDataManager credentialsDataManager;
    private final VaultServices vaultServices;

    @Autowired
    public CredentialProcessor(CredentialsDataManager credentialsDataManager, VaultServices vaultServices) {
        this.credentialsDataManager = credentialsDataManager;
        this.vaultServices = vaultServices;
    }
    public void savePasswordCredential(String profileId, String tenantId, String transitEncryptedPassword){
        String decryptedTransitPassword  =this.vaultServices.decryptFromTransit(transitEncryptedPassword);
        String hashedPassword = this.vaultServices.hashPassword(decryptedTransitPassword);
        String encryptedPassword = this.vaultServices.encryptUsingCredentialStoreKey(hashedPassword);
        this.credentialsDataManager.savePasswordCredentialDocument(profileId, tenantId, encryptedPassword);
    }
}

package com.heimdallauth.credentialstore.services;

import com.heimdallauth.credentialstore.clients.KratosClient;
import com.heimdallauth.credentialstore.constants.CredentialType;
import com.heimdallauth.credentialstore.constants.CredentialValidationResponse;
import com.heimdallauth.credentialstore.dao.CredentialsDataManager;
import com.heimdallauth.credentialstore.dao.PasswordCredentialDocument;
import com.heimdallauth.credentialstore.dto.CredentialValidationResponseDTO;
import com.heimdallauth.credentialstore.dto.UserInformationDTO;
import com.heimdallauth.credentialstore.exceptions.CredentialNotFoundInDB;
import com.heimdallauth.credentialstore.exceptions.CredentialValidationFailed;
import com.heimdallauth.credentialstore.exceptions.CredentialsAreExpired;
import com.heimdallauth.credentialstore.exceptions.UserProfileIdInvalid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CredentialProcessor {
    private final CredentialsDataManager credentialsDataManager;
    private final KratosClient kratosClient;
    private final VaultServices vaultServices;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CredentialProcessor(CredentialsDataManager credentialsDataManager, KratosClient kratosClient, VaultServices vaultServices, PasswordEncoder passwordEncoder) {
        this.credentialsDataManager = credentialsDataManager;
        this.kratosClient = kratosClient;
        this.vaultServices = vaultServices;
        this.passwordEncoder = passwordEncoder;
    }
    public void savePasswordCredential(String profileId, String tenantId, String transitEncryptedPassword) throws UserProfileIdInvalid {
        this.validateUserProfileId(profileId);
        String decryptedTransitPassword = this.vaultServices.decryptFromTransit(transitEncryptedPassword);
        String hashedPassword = this.vaultServices.hashPassword(decryptedTransitPassword);
        String encryptedPassword = this.vaultServices.encryptUsingCredentialStoreKey(hashedPassword);
        this.credentialsDataManager.savePasswordCredentialDocument(profileId, tenantId, encryptedPassword);
    }

    public String validateUserPasswordCredential(String profileId, String transitEncryptedPassword) {
        String decryptedTransitPassword = this.vaultServices.decryptFromTransit(transitEncryptedPassword);
        PasswordCredentialDocument passwordCredentialDocument = this.credentialsDataManager.findPasswordCredentialByProfileResourceNumber(profileId).orElseThrow(() -> new CredentialNotFoundInDB("Requested credentials could not be found in DB"));
        if (Instant.now().isBefore(passwordCredentialDocument.getExpiresOn())) {
            // Credential is still valid
            Boolean isCredentialValidated = this.vaultServices.isPasswordValid(this.vaultServices.decryptUsingCredentialStoreKey(passwordCredentialDocument.getCipherText()), decryptedTransitPassword);
            if (isCredentialValidated) {
                CredentialValidationResponseDTO response = new CredentialValidationResponseDTO(
                        CredentialValidationResponse.CREDENTIAL_IS_VALID,
                        profileId,
                        "",
                        CredentialType.PASSWORD_CREDENTIAL.toString()
                );
                return this.vaultServices.encryptUsingCommonKey(response);
            }
        } else {
            throw new CredentialValidationFailed("Credential validation failed");
        }
        throw new CredentialsAreExpired("Credentials are expired", profileId);
    }
    private void validateUserProfileId(String profileId){
        UserInformationDTO fetchedUserInformation = this.kratosClient.fetchUserProfileInformation(profileId);
        if (fetchedUserInformation == null) {
            throw new UserProfileIdInvalid("User profile not found", profileId);
        }
    }
}

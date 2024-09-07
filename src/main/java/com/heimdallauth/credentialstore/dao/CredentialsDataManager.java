package com.heimdallauth.credentialstore.dao;

import java.util.Optional;

public interface CredentialsDataManager {
    PasswordCredentialDocument savePasswordCredentialDocument(String profileId, String tenantId, String encryptedPassword);
    Optional<PasswordCredentialDocument> findPasswordCredentialByProfileResourceNumber(String profileResourceNumber);
    void deletePasswordCredentialByProfileResourceNumber(String profileResourceNumber);
}

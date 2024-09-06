package com.heimdallauth.credentialstore.dao.impl;

import com.heimdallauth.credentialstore.constants.CredentialClassifier;
import com.heimdallauth.credentialstore.constants.CredentialType;
import com.heimdallauth.credentialstore.dao.CredentialsDataManager;
import com.heimdallauth.credentialstore.dao.PasswordCredentialDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
@Component
public class CredentialNonRelationalDataManager implements CredentialsDataManager {
    private static final String PASSWORD_CREDENTIAL_DOCUMENTS_COLLECTION = "password-credential-documents";
    private final MongoTemplate mongoTemplate;

    @Autowired
    public CredentialNonRelationalDataManager(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public PasswordCredentialDocument savePasswordCredentialDocument(String profileId, String tenantId, String hashedPassword) {
        PasswordCredentialDocument passwordCredentialDocument = PasswordCredentialDocument.builder()
                .profileResourceIdentifier(buildProfileResourceIdentifier(profileId, tenantId, CredentialClassifier.AUTHENTICATION_CREDENTIAL,CredentialType.PASSWORD_CREDENTIAL))
                .cipherText(hashedPassword)
                .createdOn(Instant.now())
                .expiresOn(Instant.now().plus(90, ChronoUnit.DAYS))
                .build();
        return this.mongoTemplate.save(passwordCredentialDocument, PASSWORD_CREDENTIAL_DOCUMENTS_COLLECTION);
    }

    @Override
    public Optional<PasswordCredentialDocument> findPasswordCredentialByProfileResourceNumber(String profileResourceNumber) {
        return Optional.empty();
    }

    @Override
    public void deletePasswordCredentialByProfileResourceNumber(String profileResourceNumber) {

    }
    private static String buildProfileResourceIdentifier(String profileId, String tenantId, CredentialClassifier classifier, CredentialType type){
        return String.format("%s::%s::%s/%s", tenantId, classifier, type, profileId);
    }
}

package com.heimdallauth.credentialstore.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document
public class PasswordCredentialDocument {
    @Id
    private String credentialId;
    @Indexed
    private String profileResourceIdentifier;
    private String cipherText;
    private Instant createdOn;
    private Instant expiresOn;
    private Boolean isActive;
}

package com.heimdallauth.credentialstore.dto;

public record CreatePasswordCredential(
        String profileId,
        String tenantId,
        String encryptedPassword
) {
}

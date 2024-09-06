package com.heimdallauth.credentialstore.dto;

public record CredentialValidationResponse(
        boolean valid,
        String profileId,
        String tenantId,
        String credentialType
) {
}

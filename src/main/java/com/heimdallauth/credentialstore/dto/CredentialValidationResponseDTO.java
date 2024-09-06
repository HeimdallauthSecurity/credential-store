package com.heimdallauth.credentialstore.dto;

import com.heimdallauth.credentialstore.constants.CredentialValidationResponse;

public record CredentialValidationResponseDTO(
        CredentialValidationResponse validationResponse,
        String profileId,
        String tenantId,
        String credentialType
) {
}

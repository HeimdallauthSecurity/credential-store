package com.heimdallauth.credentialstore.dto;

import java.time.Instant;

public record UserPasswordCredentialValidationRequest(
        String profileId,
        String transitEncryptedPassword,
        Instant validationRequestTimestamp

) {
}

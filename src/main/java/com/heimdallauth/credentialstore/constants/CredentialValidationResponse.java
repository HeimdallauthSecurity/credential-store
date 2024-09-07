package com.heimdallauth.credentialstore.constants;

public enum CredentialValidationResponse {
    CREDENTIAL_IS_VALID,
    CREDENTIAL_IS_VALID_BUT_EXPIRED,
    CREDENTIAL_IS_VALID_BUT_ABOUT_TO_EXPIRE,
    CREDENTIAL_IS_INVALID
}

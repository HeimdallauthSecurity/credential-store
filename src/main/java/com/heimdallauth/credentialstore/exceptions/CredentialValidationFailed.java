package com.heimdallauth.credentialstore.exceptions;

public class CredentialValidationFailed extends RuntimeException {
    public CredentialValidationFailed(String message) {
        super(message);
    }
}

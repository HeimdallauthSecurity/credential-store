package com.heimdallauth.credentialstore.exceptions;

public class CredentialsAreExpired extends RuntimeException {
    public final String profileId;
    public CredentialsAreExpired(String message, String profileId) {
        super(message);
        this.profileId = profileId;
    }
}

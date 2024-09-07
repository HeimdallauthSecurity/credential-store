package com.heimdallauth.credentialstore.exceptions;

public class UserProfileIdInvalid extends RuntimeException {
    public final String profileId;
    public UserProfileIdInvalid(String message, String profileId) {
        super(message);
        this.profileId = profileId;
    }
}

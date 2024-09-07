package com.heimdallauth.credentialstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoder{
    private final Argon2PasswordEncoder argon2PasswordEncoder;

    public PasswordEncoder() {
        this.argon2PasswordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }
    protected String encodePassword(String password){
        return argon2PasswordEncoder.encode(password);
    }
    protected boolean matches(String passwordToValidate, String storedPassword){
        return argon2PasswordEncoder.matches(passwordToValidate, storedPassword);
    }
}

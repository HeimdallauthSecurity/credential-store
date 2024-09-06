package com.heimdallauth.credentialstore.controller;

import com.heimdallauth.credentialstore.dto.UserPasswordCredentialValidationRequest;
import com.heimdallauth.credentialstore.services.CredentialProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validate")
public class ValidationController {
    private final CredentialProcessor credentialProcessor;

    @Autowired
    public ValidationController(CredentialProcessor credentialProcessor) {
        this.credentialProcessor = credentialProcessor;
    }

    @PostMapping("/user/credential")
    public ResponseEntity<String> validateUserCredential(UserPasswordCredentialValidationRequest passwordValidationPayload){
        return ResponseEntity.status(HttpStatus.OK).body(this.credentialProcessor.validateUserPasswordCredential(passwordValidationPayload.profileId(), passwordValidationPayload.transitEncryptedPassword()));
    }
}

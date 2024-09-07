package com.heimdallauth.credentialstore.controller;

import com.heimdallauth.credentialstore.dto.CreatePasswordCredential;
import com.heimdallauth.credentialstore.services.CredentialProcessor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/credentials")
@Tag(name = "Credentials", description = "Operations related to credentials")
public class CredentialController {
    private final CredentialProcessor credentialProcessor;

    public CredentialController(CredentialProcessor credentialProcessor) {
        this.credentialProcessor = credentialProcessor;
    }

    @PostMapping("/create/password")
    @Operation(summary = "Create Password Credential", description = "Creates a new password credential")
    public void createPasswordCredential(@RequestBody CreatePasswordCredential passwordCredentialPayload) {
        this.credentialProcessor.savePasswordCredential(passwordCredentialPayload.profileId(), passwordCredentialPayload.tenantId(), passwordCredentialPayload.transitEncryptedPassword());
    }
}
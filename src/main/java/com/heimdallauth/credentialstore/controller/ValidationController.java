package com.heimdallauth.credentialstore.controller;

import com.heimdallauth.credentialstore.dto.CredentialValidationResponse;
import com.heimdallauth.credentialstore.dto.UserPasswordCredentialValidationRequest;
import com.heimdallauth.credentialstore.services.CredentialProcessor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validate")
@Tag(name = "Validation", description = "APIs for validating user credentials")
public class ValidationController {
    private final CredentialProcessor credentialProcessor;

    @Autowired
    public ValidationController(CredentialProcessor credentialProcessor) {
        this.credentialProcessor = credentialProcessor;
    }

    @PostMapping("/user/credential")
    @Operation(summary = "Validate user credential", description = "Validates the user credential based on the provided profile ID and encrypted password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Validation successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CredentialValidationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<String> validateUserCredential(@RequestBody UserPasswordCredentialValidationRequest passwordValidationPayload){
        return ResponseEntity.status(HttpStatus.OK).body(this.credentialProcessor.validateUserPasswordCredential(passwordValidationPayload.profileId(), passwordValidationPayload.transitEncryptedPassword()));
    }
}

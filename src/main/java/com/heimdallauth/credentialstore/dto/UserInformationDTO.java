package com.heimdallauth.credentialstore.dto;

public record UserInformationDTO(
        String id,
        String username,
        String email,
        String firstName,
        String lastName
) {
}

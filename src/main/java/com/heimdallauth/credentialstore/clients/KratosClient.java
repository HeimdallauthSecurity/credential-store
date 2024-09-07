package com.heimdallauth.credentialstore.clients;

import com.heimdallauth.credentialstore.dto.UserInformationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "kratos-client",url = "${kratos.url}")
public interface KratosClient {
    @GetMapping("/users/id/{profileId}")
    UserInformationDTO fetchUserProfileInformation(@PathVariable String profileId);
}

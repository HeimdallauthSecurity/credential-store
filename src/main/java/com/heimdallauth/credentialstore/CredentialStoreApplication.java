package com.heimdallauth.credentialstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CredentialStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CredentialStoreApplication.class, args);
    }

}

package com.heimdallauth.credentialstore.exceptions;

public class CredentialNotFoundInDB extends RuntimeException {
  public CredentialNotFoundInDB(String message) {
    super(message);
  }
}

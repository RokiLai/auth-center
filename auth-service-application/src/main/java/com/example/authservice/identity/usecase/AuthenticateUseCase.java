package com.example.authservice.identity.usecase;

import com.example.authservice.identity.query.CurrentIdentity;

public interface AuthenticateUseCase {

    CurrentIdentity authenticate(String rawToken);
}

package com.example.authservice.identity.usecase;

import com.example.authservice.domain.identity.model.result.CurrentIdentity;

public interface AuthenticateUseCase {

    CurrentIdentity authenticate(String rawToken);
}

package com.example.authservice.domain.identity.repository;

import com.example.authservice.domain.identity.model.IdentityAccount;

public interface IdentityAccountRepository {

    IdentityAccount findByUsername(String username);
}

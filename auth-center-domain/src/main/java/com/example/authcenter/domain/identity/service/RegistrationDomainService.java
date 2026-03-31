package com.example.authcenter.domain.identity.service;

import com.example.authcenter.domain.identity.model.entity.IdentityAccount;

public interface RegistrationDomainService {

    IdentityAccount register(String username, String password, String email);
}

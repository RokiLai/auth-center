package com.example.authcenter.domain.identity.repository;

import com.example.authcenter.domain.identity.model.entity.IdentityAccount;

public interface IdentityAccountRepository {

    boolean existsByUsername(String username);

    IdentityAccount findByUsername(String username);

    void save(IdentityAccount account);
}

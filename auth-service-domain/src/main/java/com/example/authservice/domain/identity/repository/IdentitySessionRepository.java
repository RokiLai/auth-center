package com.example.authservice.domain.identity.repository;

import com.example.authservice.domain.identity.model.IdentitySession;

public interface IdentitySessionRepository {

    void save(IdentitySession session);

    IdentitySession findBySessionId(String sessionId);

    IdentitySession findByAccountId(Long accountId);

    void deleteBySessionId(String sessionId);

    void deleteByAccountId(Long accountId);
}

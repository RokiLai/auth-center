package com.example.authservice.domain.identity.service;

import com.example.authservice.domain.identity.model.valueobject.TokenClaims;

public interface IdentityTokenProvider {

    String issue(Long userId, String username, String sessionId);

    TokenClaims parse(String rawToken);
}

package com.example.authservice.infra.identity.service;

import com.example.authservice.domain.identity.model.TokenClaims;
import com.example.authservice.domain.identity.service.IdentityTokenProvider;
import com.example.authservice.util.JwtUtil;
import org.springframework.stereotype.Component;

@Component
public class JwtIdentityTokenProvider implements IdentityTokenProvider {

    private final JwtUtil jwtUtil;

    public JwtIdentityTokenProvider(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String issue(Long userId, String username, String sessionId) {
        return jwtUtil.generateToken(userId, username, sessionId);
    }

    @Override
    public TokenClaims parse(String rawToken) {
        return new TokenClaims(
                jwtUtil.parseUserId(rawToken),
                jwtUtil.parseUsername(rawToken),
                jwtUtil.parseSessionId(rawToken),
                rawToken
        );
    }
}

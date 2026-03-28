package com.example.authservice.domain.identity.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenClaims {
    private Long userId;
    private String username;
    private String sessionId;
    private String rawToken;
}

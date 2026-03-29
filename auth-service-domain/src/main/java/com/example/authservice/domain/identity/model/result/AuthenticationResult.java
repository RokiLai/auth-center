package com.example.authservice.domain.identity.model.result;

import com.example.authservice.domain.identity.model.entity.IdentityAccount;
import com.example.authservice.domain.identity.model.entity.IdentitySession;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticationResult {
    private final IdentityAccount account;
    private final IdentitySession session;
}

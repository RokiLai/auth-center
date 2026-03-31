package com.example.authcenter.domain.identity.service.impl;

import com.example.authcenter.domain.identity.model.context.SessionRevocationPlan;
import com.example.authcenter.domain.identity.repository.IdentitySessionRepository;
import com.example.authcenter.domain.identity.service.SessionDomainService;
import com.example.authcenter.exception.auth.TokenInvalidException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
public class SessionDomainServiceImpl implements SessionDomainService {

    private final IdentitySessionRepository identitySessionRepository;

    public SessionDomainServiceImpl(IdentitySessionRepository identitySessionRepository) {
        this.identitySessionRepository = identitySessionRepository;
    }

    @Override
    public SessionRevocationPlan logout(Long accountId, String currentSessionId) {
        if (accountId == null || !StringUtils.hasText(currentSessionId)) {
            throw new TokenInvalidException();
        }

        String boundSessionId = identitySessionRepository.findSessionIdByAccountId(accountId);
        Long accountIdBindingToDelete = Objects.equals(boundSessionId, currentSessionId) ? accountId : null;
        return new SessionRevocationPlan(currentSessionId, accountIdBindingToDelete);
    }
}

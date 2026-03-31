package com.example.authcenter.domain.identity.service.impl;

import com.example.authcenter.domain.identity.model.context.PasswordChangeDecision;
import com.example.authcenter.domain.identity.model.context.SessionRevocationPlan;
import com.example.authcenter.domain.identity.model.entity.IdentityAccount;
import com.example.authcenter.domain.identity.model.valueobject.RawPassword;
import com.example.authcenter.domain.identity.repository.IdentityAccountRepository;
import com.example.authcenter.domain.identity.repository.IdentitySessionRepository;
import com.example.authcenter.domain.identity.service.CredentialDomainService;
import com.example.authcenter.domain.identity.service.PasswordHasher;
import com.example.authcenter.exception.auth.OldPasswordIncorrectException;
import com.example.authcenter.exception.auth.TokenInvalidException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
public class CredentialDomainServiceImpl implements CredentialDomainService {

    private final IdentityAccountRepository identityAccountRepository;
    private final IdentitySessionRepository identitySessionRepository;
    private final PasswordHasher passwordHasher;

    public CredentialDomainServiceImpl(IdentityAccountRepository identityAccountRepository,
                                       IdentitySessionRepository identitySessionRepository,
                                       PasswordHasher passwordHasher) {
        this.identityAccountRepository = identityAccountRepository;
        this.identitySessionRepository = identitySessionRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public PasswordChangeDecision changePassword(Long accountId,
                                                 String username,
                                                 String currentSessionId,
                                                 String oldPassword,
                                                 String newPassword) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(currentSessionId)) {
            throw new TokenInvalidException();
        }

        IdentityAccount account = identityAccountRepository.findByUsername(username);
        if (account == null || !account.matchPassword(new RawPassword(oldPassword), passwordHasher)) {
            throw new OldPasswordIncorrectException();
        }

        account.updatePassword(new RawPassword(newPassword), passwordHasher);

        return new PasswordChangeDecision(account, buildRevocationPlan(accountId, currentSessionId));
    }

    private SessionRevocationPlan buildRevocationPlan(Long accountId, String currentSessionId) {
        if (accountId == null) {
            return SessionRevocationPlan.none();
        }

        String boundSessionId = identitySessionRepository.findSessionIdByAccountId(accountId);
        Long accountIdBindingToDelete = Objects.equals(boundSessionId, currentSessionId) ? accountId : null;
        return new SessionRevocationPlan(currentSessionId, accountIdBindingToDelete);
    }
}

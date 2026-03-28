package com.example.authservice.identity.usecase.impl;

import com.example.authservice.auth.IdentityContext;
import com.example.authservice.auth.IdentityContextHolder;
import com.example.authservice.domain.identity.model.IdentitySession;
import com.example.authservice.domain.identity.repository.IdentitySessionRepository;
import com.example.authservice.exception.AuthErrorCode;
import com.example.authservice.identity.usecase.LogoutUseCase;
import com.roki.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class LogoutUseCaseImpl implements LogoutUseCase {

    private final IdentitySessionRepository identitySessionRepository;

    public LogoutUseCaseImpl(IdentitySessionRepository identitySessionRepository) {
        this.identitySessionRepository = identitySessionRepository;
    }

    @Override
    public boolean logout() {
        IdentityContext currentAccount = IdentityContextHolder.get();
        if (currentAccount == null || currentAccount.getId() == null) {
            throw new BusinessException(AuthErrorCode.TOKEN_INVALID);
        }

        IdentitySession session = identitySessionRepository.findByAccountId(currentAccount.getId());
        if (session != null && session.getSessionId() != null && !session.getSessionId().isBlank()) {
            identitySessionRepository.deleteBySessionId(session.getSessionId());
        }
        identitySessionRepository.deleteByAccountId(currentAccount.getId());
        return true;
    }
}

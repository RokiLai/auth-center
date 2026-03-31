package com.example.authcenter.identity.usecase.impl;

import com.example.authcenter.application.context.CurrentOperator;
import com.example.authcenter.domain.identity.model.context.SessionRevocationPlan;
import com.example.authcenter.domain.identity.repository.IdentitySessionRepository;
import com.example.authcenter.domain.identity.service.SessionDomainService;
import com.example.authcenter.exception.auth.TokenInvalidException;
import com.example.authcenter.identity.usecase.LogoutUseCase;
import com.example.authcenter.identity.usecase.command.LogoutCommand;
import org.springframework.stereotype.Service;

@Service
public class LogoutUseCaseImpl implements LogoutUseCase {

    private final IdentitySessionRepository identitySessionRepository;
    private final SessionDomainService sessionDomainService;

    public LogoutUseCaseImpl(IdentitySessionRepository identitySessionRepository,
                             SessionDomainService sessionDomainService) {
        this.identitySessionRepository = identitySessionRepository;
        this.sessionDomainService = sessionDomainService;
    }

    @Override
    public boolean logout(LogoutCommand command) {
        CurrentOperator operator = command == null ? null : command.operator();
        if (operator == null) {
            throw new TokenInvalidException();
        }

        SessionRevocationPlan plan = sessionDomainService.logout(operator.id(), operator.sessionId());
        if (plan.sessionIdToDelete() != null) {
            identitySessionRepository.deleteBySessionId(plan.sessionIdToDelete());
        }
        if (plan.accountIdBindingToDelete() != null) {
            identitySessionRepository.deleteByAccountId(plan.accountIdBindingToDelete());
        }
        return true;
    }
}

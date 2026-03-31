package com.example.authcenter.identity.usecase.impl;

import com.example.authcenter.application.context.CurrentOperator;
import com.example.authcenter.domain.identity.model.context.PasswordChangeDecision;
import com.example.authcenter.domain.identity.repository.IdentityAccountRepository;
import com.example.authcenter.domain.identity.repository.IdentitySessionRepository;
import com.example.authcenter.domain.identity.service.CredentialDomainService;
import com.example.authcenter.exception.auth.TokenInvalidException;
import com.example.authcenter.identity.usecase.UpdatePasswordUseCase;
import com.example.authcenter.identity.usecase.command.UpdatePasswordCommand;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UpdatePasswordUseCaseImpl implements UpdatePasswordUseCase {

    private final IdentityAccountRepository identityAccountRepository;
    private final IdentitySessionRepository identitySessionRepository;
    private final CredentialDomainService credentialDomainService;

    public UpdatePasswordUseCaseImpl(IdentityAccountRepository identityAccountRepository,
                                     IdentitySessionRepository identitySessionRepository,
                                     CredentialDomainService credentialDomainService) {
        this.identityAccountRepository = identityAccountRepository;
        this.identitySessionRepository = identitySessionRepository;
        this.credentialDomainService = credentialDomainService;
    }

    @Override
    public boolean updatePassword(UpdatePasswordCommand command) {
        CurrentOperator operator = command == null ? null : command.operator();
        if (operator == null || !StringUtils.hasText(operator.username()) || !StringUtils.hasText(operator.sessionId())) {
            throw new TokenInvalidException();
        }

        PasswordChangeDecision decision = credentialDomainService.changePassword(
                operator.id(),
                operator.username(),
                operator.sessionId(),
                command.oldPassword(),
                command.newPassword()
        );
        identityAccountRepository.save(decision.account());
        if (StringUtils.hasText(decision.sessionRevocationPlan().sessionIdToDelete())) {
            identitySessionRepository.deleteBySessionId(decision.sessionRevocationPlan().sessionIdToDelete());
        }
        if (decision.sessionRevocationPlan().accountIdBindingToDelete() != null) {
            identitySessionRepository.deleteByAccountId(decision.sessionRevocationPlan().accountIdBindingToDelete());
        }
        return true;
    }
}

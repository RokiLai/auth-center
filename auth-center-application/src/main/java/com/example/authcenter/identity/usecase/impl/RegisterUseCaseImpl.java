package com.example.authcenter.identity.usecase.impl;

import com.example.authcenter.domain.identity.model.entity.IdentityAccount;
import com.example.authcenter.domain.identity.repository.IdentityAccountRepository;
import com.example.authcenter.domain.identity.service.RegistrationDomainService;
import com.example.authcenter.identity.usecase.RegisterUseCase;
import com.example.authcenter.identity.usecase.command.RegisterCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterUseCaseImpl implements RegisterUseCase {

    private final IdentityAccountRepository identityAccountRepository;
    private final RegistrationDomainService registrationDomainService;

    public RegisterUseCaseImpl(IdentityAccountRepository identityAccountRepository,
                               RegistrationDomainService registrationDomainService) {
        this.identityAccountRepository = identityAccountRepository;
        this.registrationDomainService = registrationDomainService;
    }

    @Override
    @Transactional
    public boolean register(RegisterCommand command) {
        IdentityAccount account = registrationDomainService.register(
                command.username(),
                command.password(),
                command.email()
        );
        identityAccountRepository.save(account);
        return true;
    }
}

package com.example.authcenter.domain.identity.service.impl;

import com.example.authcenter.domain.identity.model.entity.IdentityAccount;
import com.example.authcenter.domain.identity.model.entity.IdentityAccountFactory;
import com.example.authcenter.domain.identity.model.valueobject.RawPassword;
import com.example.authcenter.domain.identity.repository.IdentityAccountRepository;
import com.example.authcenter.domain.identity.service.PasswordHasher;
import com.example.authcenter.domain.identity.service.RegistrationDomainService;
import com.example.authcenter.exception.auth.UsernameAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public class RegistrationDomainServiceImpl implements RegistrationDomainService {

    private final IdentityAccountRepository identityAccountRepository;
    private final PasswordHasher passwordHasher;
    private final IdentityAccountFactory identityAccountFactory;

    public RegistrationDomainServiceImpl(IdentityAccountRepository identityAccountRepository,
                                         PasswordHasher passwordHasher,
                                         IdentityAccountFactory identityAccountFactory) {
        this.identityAccountRepository = identityAccountRepository;
        this.passwordHasher = passwordHasher;
        this.identityAccountFactory = identityAccountFactory;
    }

    @Override
    public IdentityAccount register(String username, String password, String email) {
        if (identityAccountRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException();
        }
        return identityAccountFactory.register(
                username,
                new RawPassword(password),
                email,
                passwordHasher
        );
    }
}

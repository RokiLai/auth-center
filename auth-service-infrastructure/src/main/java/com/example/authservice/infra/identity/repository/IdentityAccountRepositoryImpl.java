package com.example.authservice.infra.identity.repository;

import com.example.authservice.domain.identity.model.IdentityAccount;
import com.example.authservice.domain.identity.model.PasswordHash;
import com.example.authservice.domain.identity.repository.IdentityAccountRepository;
import com.example.authservice.domain.model.Account;
import com.example.authservice.domain.repo.AccountRepo;
import org.springframework.stereotype.Repository;

@Repository
public class IdentityAccountRepositoryImpl implements IdentityAccountRepository {

    private final AccountRepo accountRepo;

    public IdentityAccountRepositoryImpl(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public IdentityAccount findByUsername(String username) {
        Account account = accountRepo.findByUsername(username);
        if (account == null) {
            return null;
        }
        return new IdentityAccount(
                account.getId(),
                account.getUsername(),
                new PasswordHash(account.getPassword()),
                account.getEmail(),
                account.getRoleIds()
        );
    }
}

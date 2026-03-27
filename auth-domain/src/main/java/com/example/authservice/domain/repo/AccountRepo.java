package com.example.authservice.domain.repo;

import com.example.authservice.domain.model.Account;

public interface AccountRepo {

    Account findByUsername(String username);

    void save(Account account);

    void updateAccountRole(Account account);
}
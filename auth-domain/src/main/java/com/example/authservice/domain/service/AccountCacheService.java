package com.example.authservice.domain.service;

import com.example.authservice.auth.AccountInfo;

public interface AccountCacheService {

    void saveAccountInfo(AccountInfo accountInfo);

    AccountInfo getAccountInfo(String username);

    void deleteAccountInfo(String username);

}

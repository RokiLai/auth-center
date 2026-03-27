package com.example.authservice.service.impl;

import com.example.authservice.auth.AccountContextHolder;
import com.example.authservice.auth.AccountInfo;
import com.example.authservice.domain.repo.*;
import com.example.authservice.domain.service.AccountCacheService;
import com.example.authservice.exception.AuthErrorCode;
import com.example.authservice.service.AccountService;
import com.example.authservice.service.dto.UserLoginDTO;
import com.example.authservice.domain.model.Account;
import com.example.authservice.util.JwtUtil;
import com.roki.exception.BusinessException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AccountCacheService accountCacheService;

    @Autowired
    private RolePermissionRepo rolePermissionRepo;

    @Autowired
    private RoleRepo roleRepo;

    private final PermissionRepo permissionRepo;

    public AccountServiceImpl(AccountRepo accountRepo, JwtUtil jwtUtil, AccountCacheService accountCacheService,
                              RolePermissionRepo rolePermissionRepo, RoleRepo roleRepo, PermissionRepo permissionRepo) {
        this.accountRepo = accountRepo;
        this.jwtUtil = jwtUtil;
        this.accountCacheService = accountCacheService;
        this.rolePermissionRepo = rolePermissionRepo;
        this.roleRepo = roleRepo;
        this.permissionRepo = permissionRepo;
    }

    @Override
    public boolean register(String username, String password, String email, List<Long> roleIds) {
        Account account = accountRepo.findByUsername(username);
        if (account != null) {
            throw new BusinessException(AuthErrorCode.USERNAME_ALREADY_EXISTS);
        }
        account = Account.register(username, password, email);
        accountRepo.save(account);

        if (!CollectionUtils.isEmpty(roleIds)) {
            account.setRoleIds(roleIds);
            accountRepo.updateAccountRole(account);
        }
        return true;
    }

    @Override
    public UserLoginDTO login(String username, String password) {
        Account account = accountRepo.findByUsername(username);
        if (account == null || !account.matchPassword(password)) {
            throw new BusinessException(AuthErrorCode.LOGIN_FAILED);
        }

        String token = jwtUtil.generateToken(username);
        UserLoginDTO result = new UserLoginDTO(account.getId(), account.getUsername(), account.getEmail(), token);
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setId(account.getId());
        accountInfo.setUsername(account.getUsername());
        accountInfo.setToken(token);
        if (!CollectionUtils.isEmpty(account.getRoleIds())) {
            accountInfo.setRole(roleRepo.selectCodeByIds(account.getRoleIds()));
            List<Long> permissionIds = rolePermissionRepo.findPermissionIdsByRoleIds(account.getRoleIds());
            accountInfo.setPermissions(permissionRepo.selectCodeByIds(permissionIds));
        }
        accountCacheService.saveAccountInfo(accountInfo);
        return result;
    }

    @Override
    public boolean validatePassword(String username, String password) {
        Account account = accountRepo.findByUsername(username);
        return account != null && account.matchPassword(password);
    }

    @Override
    public boolean updatePassword(String oldPassword, String newPassword) {
        String username = AccountContextHolder.get().getUsername();
        Account account = accountRepo.findByUsername(username);
        if (account == null || !account.matchPassword(oldPassword)) {
            throw new BusinessException(AuthErrorCode.OLD_PASSWORD_INCORRECT);
        }
        account.updatePassword(newPassword);
        accountRepo.save(account);
        accountCacheService.deleteAccountInfo(username);
        return true;
    }

    @Override
    public AccountInfo getAccountInfo(String username) {
        return accountCacheService.getAccountInfo(username);
    }

}

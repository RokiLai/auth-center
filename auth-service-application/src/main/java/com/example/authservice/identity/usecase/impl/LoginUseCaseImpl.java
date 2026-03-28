package com.example.authservice.identity.usecase.impl;

import com.example.authservice.domain.identity.model.IdentityAccount;
import com.example.authservice.domain.identity.model.IdentitySession;
import com.example.authservice.domain.identity.model.RawPassword;
import com.example.authservice.domain.identity.repository.IdentityAccountRepository;
import com.example.authservice.domain.identity.repository.IdentitySessionRepository;
import com.example.authservice.domain.identity.service.IdentityTokenProvider;
import com.example.authservice.domain.identity.service.PasswordHasher;
import com.example.authservice.domain.repo.PermissionRepo;
import com.example.authservice.domain.repo.RolePermissionRepo;
import com.example.authservice.domain.repo.RoleRepo;
import com.example.authservice.exception.AuthErrorCode;
import com.example.authservice.identity.dto.LoginResult;
import com.example.authservice.identity.usecase.LoginUseCase;
import com.roki.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

@Service
public class LoginUseCaseImpl implements LoginUseCase {

    private final IdentityAccountRepository identityAccountRepository;
    private final IdentitySessionRepository identitySessionRepository;
    private final IdentityTokenProvider identityTokenProvider;
    private final PasswordHasher passwordHasher;
    private final RolePermissionRepo rolePermissionRepo;
    private final RoleRepo roleRepo;
    private final PermissionRepo permissionRepo;

    public LoginUseCaseImpl(IdentityAccountRepository identityAccountRepository,
                            IdentitySessionRepository identitySessionRepository,
                            IdentityTokenProvider identityTokenProvider,
                            PasswordHasher passwordHasher,
                            RolePermissionRepo rolePermissionRepo,
                            RoleRepo roleRepo,
                            PermissionRepo permissionRepo) {
        this.identityAccountRepository = identityAccountRepository;
        this.identitySessionRepository = identitySessionRepository;
        this.identityTokenProvider = identityTokenProvider;
        this.passwordHasher = passwordHasher;
        this.rolePermissionRepo = rolePermissionRepo;
        this.roleRepo = roleRepo;
        this.permissionRepo = permissionRepo;
    }

    @Override
    public LoginResult login(String username, String password) {
        IdentityAccount account = identityAccountRepository.findByUsername(username);
        if (account == null || !account.matchPassword(new RawPassword(password), passwordHasher)) {
            throw new BusinessException(AuthErrorCode.LOGIN_FAILED);
        }

        IdentitySession oldSession = identitySessionRepository.findByAccountId(account.getId());
        if (oldSession != null && oldSession.getSessionId() != null && !oldSession.getSessionId().isBlank()) {
            identitySessionRepository.deleteBySessionId(oldSession.getSessionId());
            identitySessionRepository.deleteByAccountId(account.getId());
        }

        String sessionId = UUID.randomUUID().toString();
        String token = identityTokenProvider.issue(account.getId(), username, sessionId);

        IdentitySession session = new IdentitySession();
        session.setSessionId(sessionId);
        session.setAccountId(account.getId());
        session.setUsername(account.getUsername());
        session.setToken(token);

        if (!CollectionUtils.isEmpty(account.getRoleIds())) {
            List<String> roles = roleRepo.selectCodeByIds(account.getRoleIds());
            session.setRoles(roles);

            List<Long> permissionIds = rolePermissionRepo.findPermissionIdsByRoleIds(account.getRoleIds());
            List<String> permissions = permissionRepo.selectCodeByIds(permissionIds);
            session.setPermissions(permissions);
        }

        identitySessionRepository.save(session);
        return new LoginResult(account.getId(), account.getUsername(), account.getEmail(), token);
    }
}

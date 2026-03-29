package com.example.authservice.identity.usecase.impl;

import com.example.authservice.domain.identity.model.entity.IdentityAccount;
import com.example.authservice.domain.identity.model.entity.IdentitySession;
import com.example.authservice.domain.identity.model.result.AuthenticationResult;
import com.example.authservice.domain.identity.repository.IdentitySessionRepository;
import com.example.authservice.domain.identity.service.AuthenticationDomainService;
import com.example.authservice.domain.repo.PermissionRepo;
import com.example.authservice.domain.repo.RolePermissionRepo;
import com.example.authservice.domain.repo.RoleRepo;
import com.example.authservice.identity.dto.LoginResult;
import com.example.authservice.identity.usecase.LoginUseCase;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class LoginUseCaseImpl implements LoginUseCase {

    private final AuthenticationDomainService authenticationDomainService;
    private final IdentitySessionRepository identitySessionRepository;
    private final RolePermissionRepo rolePermissionRepo;
    private final RoleRepo roleRepo;
    private final PermissionRepo permissionRepo;

    public LoginUseCaseImpl(AuthenticationDomainService authenticationDomainService,
                            IdentitySessionRepository identitySessionRepository,
                            RolePermissionRepo rolePermissionRepo,
                            RoleRepo roleRepo,
                            PermissionRepo permissionRepo) {
        this.authenticationDomainService = authenticationDomainService;
        this.identitySessionRepository = identitySessionRepository;
        this.rolePermissionRepo = rolePermissionRepo;
        this.roleRepo = roleRepo;
        this.permissionRepo = permissionRepo;
    }

    @Override
    public LoginResult login(String username, String password) {
        // 认证、替换旧会话、创建新会话这些规则已下沉到领域服务。
        AuthenticationResult authenticationResult = authenticationDomainService.authenticate(username, password);
        IdentityAccount account = authenticationResult.getAccount();
        IdentitySession session = authenticationResult.getSession();

        // 角色和权限在登录时做一次快照，避免每次鉴权都回源查询。
        if (!CollectionUtils.isEmpty(account.getRoleIds())) {
            List<String> roles = roleRepo.selectCodeByIds(account.getRoleIds());
            List<Long> permissionIds = rolePermissionRepo.findPermissionIdsByRoleIds(account.getRoleIds());
            List<String> permissions = permissionRepo.selectCodeByIds(permissionIds);
            session.grantAuthorities(roles, permissions);
        }

        // 应用层负责持久化会话，并把领域结果转换成接口返回对象。
        identitySessionRepository.save(session);
        return new LoginResult(account.getId(), account.getUsername(), account.getEmail(), session.getToken());
    }
}

package com.example.authservice.infra.identity.service;

import com.example.authservice.domain.identity.model.valueobject.AuthorizationSnapshot;
import com.example.authservice.domain.identity.service.AuthorizationSnapshotProvider;
import com.example.authservice.domain.repo.PermissionRepo;
import com.example.authservice.domain.repo.RolePermissionRepo;
import com.example.authservice.domain.repo.RoleRepo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorizationSnapshotProviderImpl implements AuthorizationSnapshotProvider {

    private final RoleRepo roleRepo;
    private final RolePermissionRepo rolePermissionRepo;
    private final PermissionRepo permissionRepo;

    public AuthorizationSnapshotProviderImpl(RoleRepo roleRepo,
                                             RolePermissionRepo rolePermissionRepo,
                                             PermissionRepo permissionRepo) {
        this.roleRepo = roleRepo;
        this.rolePermissionRepo = rolePermissionRepo;
        this.permissionRepo = permissionRepo;
    }

    @Override
    public AuthorizationSnapshot loadByRoleIds(List<Long> roleIds) {
        // 这里返回的是登录时附着到会话上的授权快照，属于身份领域的不可变值对象。
        // Returns the immutable authorization snapshot attached to the login session inside the identity domain.
        List<String> roles = roleRepo.selectCodeByIds(roleIds);
        List<Long> permissionIds = rolePermissionRepo.findPermissionIdsByRoleIds(roleIds);
        List<String> permissions = permissionRepo.selectCodeByIds(permissionIds);
        return new AuthorizationSnapshot(roles, permissions);
    }
}

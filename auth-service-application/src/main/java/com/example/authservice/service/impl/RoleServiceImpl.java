package com.example.authservice.service.impl;

import com.example.authservice.domain.model.Role;
import com.example.authservice.domain.repo.RoleRepo;
import com.example.authservice.exception.AuthErrorCode;
import com.roki.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Set;

public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepo roleRepo;

    @Override
    public void batchAuthorize(Long roleId, Set<Long> permissionIds) {
        if (roleId == null || permissionIds == null) {
            throw new BusinessException(AuthErrorCode.ROLE_AUTHORIZE_PARAM_INVALID);
        }
        Role role = roleRepo.selectById(roleId);
        if (role == null) {
            return;
        }
        role.addPermissions(new ArrayList<>(permissionIds));
        roleRepo.updateRolePermission(role);
    }
}

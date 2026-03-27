package com.example.authservice.service.impl;

import com.example.authservice.domain.model.Role;
import com.example.authservice.domain.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Set;

public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepo roleRepo;

    @Override
    public void batchAuthorize(Long roleId, Set<Long> permissionIds) {
        // 参数校验前置条件
        if (roleId == null || permissionIds == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        Role role = roleRepo.selectById(roleId);
        if (role == null) {
            return;
        }
        role.addPermissions(new ArrayList<>(permissionIds));
        roleRepo.updateRolePermission(role);
    }
}

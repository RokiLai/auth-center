package com.example.authservice.domain.model;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class Role {
    private Long id;
    private String code;
    private List<Long> permissionIds;

    public void addPermissions(List<Long> newPermissions) {
        Objects.requireNonNull(newPermissions, "权限集合不能为空");
        this.permissionIds.clear();
        this.permissionIds.addAll(newPermissions);
    }

    // 领域行为：添加单个权限
    public void addPermission(Long permissionId) {
        Objects.requireNonNull(permissionId, "权限不能为空");
        this.permissionIds.add(permissionId);
    }
}

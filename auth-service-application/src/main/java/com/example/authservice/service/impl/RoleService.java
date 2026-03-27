package com.example.authservice.service.impl;

import java.util.Set;

public interface RoleService {
    /**
     * 批量授权
     * @param roleId
     * @param permissionIds
     */
    void batchAuthorize(Long roleId, Set<Long> permissionIds);
}

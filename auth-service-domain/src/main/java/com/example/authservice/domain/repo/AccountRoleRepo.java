package com.example.authservice.domain.repo;

import java.util.List;

public interface AccountRoleRepo {

    /**
     * 根据账号ID查询角色ID列表
     *
     * @param accountId 账号ID
     * @return 角色ID列表
     */
    List<Long> findRoleIdsByAccountId(Long accountId);
}

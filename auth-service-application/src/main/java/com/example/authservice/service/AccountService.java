package com.example.authservice.service;

import java.util.List;

public interface AccountService {
    /**
     * 校验用户名和密码是否匹配
     * @param username 用户名
     * @param oldPassword 旧密码
     * @return 是否匹配
     */
    boolean validatePassword(String username, String oldPassword);
    /**
     * 更新用户密码
     * @param username 用户名
     * @param newPassword 新密码
     */
    boolean updatePassword(String username, String newPassword);

    /**
     * 注册用户
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @param roleIds 角色列表
     * @return 注册结果
     */
    boolean register(String username, String password, String email, List<Long> roleIds);
    
}

package com.example.authservice.domain.model;

import io.micrometer.common.util.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Account {
    private Long id;
    private String username;
    private String password;
    private String email;
    private List<Long> roleIds;

    public Account(Long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public boolean matchPassword(String input) {
        return input != null && input.equals(this.password);
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * 工厂方法
     * @param username
     * @param rawPassword
     * @param email
     * @return
     */
    public static Account register(String username, String rawPassword, String email) {
        // 参数校验
        if (StringUtils.isBlank(username)) throw new IllegalArgumentException("用户名不能为空");
        if (StringUtils.isBlank(rawPassword) || rawPassword.length() < 6)
            throw new IllegalArgumentException("密码过短");
        if (!email.contains("@")) throw new IllegalArgumentException("邮箱格式不正确");

        Account account = new Account();
        account.username = username;
        account.password = rawPassword;
        account.email = email;
        return account;
    }

}

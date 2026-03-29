package com.example.authservice.domain.model;

import com.example.authservice.domain.identity.model.valueobject.PasswordHash;
import com.example.authservice.domain.identity.model.valueobject.RawPassword;
import com.example.authservice.domain.identity.service.PasswordHasher;
import com.example.authservice.exception.AuthErrorCode;
import com.roki.exception.BusinessException;
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

    public boolean matchPassword(RawPassword rawPassword, PasswordHasher passwordHasher) {
        return rawPassword != null && passwordHasher.matches(rawPassword, new PasswordHash(password));
    }

    public void updatePassword(RawPassword newPassword, PasswordHasher passwordHasher) {
        if (newPassword == null || StringUtils.isBlank(newPassword.getValue()) || newPassword.getValue().length() < 6) {
            throw new BusinessException(AuthErrorCode.PASSWORD_TOO_SHORT);
        }
        this.password = passwordHasher.encode(newPassword).getValue();
    }

    /**
     * 工厂方法
     * @param username
     * @param rawPassword
     * @param email
     * @return
     */
    public static Account register(String username, RawPassword rawPassword, String email, PasswordHasher passwordHasher) {
        if (StringUtils.isBlank(username)) {
            throw new BusinessException(AuthErrorCode.USERNAME_REQUIRED);
        }
        if (rawPassword == null || StringUtils.isBlank(rawPassword.getValue()) || rawPassword.getValue().length() < 6) {
            throw new BusinessException(AuthErrorCode.PASSWORD_TOO_SHORT);
        }
        if (StringUtils.isBlank(email) || !email.contains("@")) {
            throw new BusinessException(AuthErrorCode.EMAIL_INVALID);
        }

        Account account = new Account();
        account.username = username;
        account.password = passwordHasher.encode(rawPassword).getValue();
        account.email = email;
        return account;
    }

}

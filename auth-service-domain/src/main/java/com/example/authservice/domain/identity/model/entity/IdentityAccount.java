package com.example.authservice.domain.identity.model.entity;

import com.example.authservice.domain.identity.model.valueobject.PasswordHash;
import com.example.authservice.domain.identity.model.valueobject.RawPassword;
import com.example.authservice.domain.identity.service.PasswordHasher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdentityAccount {
    private Long id;
    private String username;
    private PasswordHash passwordHash;
    private String email;
    private List<Long> roleIds;

    /**
     * 认证阶段只关心原始密码是否能匹配当前账号的密码摘要。
     */
    public boolean matchPassword(RawPassword rawPassword, PasswordHasher passwordHasher) {
        return rawPassword != null && passwordHasher.matches(rawPassword, passwordHash);
    }
}

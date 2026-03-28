package com.example.authservice.domain.identity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.example.authservice.domain.identity.service.PasswordHasher;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdentityAccount {
    private Long id;
    private String username;
    private PasswordHash passwordHash;
    private String email;
    private List<Long> roleIds;

    public boolean matchPassword(RawPassword rawPassword, PasswordHasher passwordHasher) {
        return rawPassword != null && passwordHasher.matches(rawPassword, passwordHash);
    }
}

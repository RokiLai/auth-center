package com.example.authservice.infra.identity.service;

import com.example.authservice.domain.identity.model.PasswordHash;
import com.example.authservice.domain.identity.model.RawPassword;
import com.example.authservice.domain.identity.service.PasswordHasher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class BcryptCompatiblePasswordHasher implements PasswordHasher {

    private final BCryptPasswordEncoder delegate = new BCryptPasswordEncoder();

    @Override
    public PasswordHash encode(RawPassword rawPassword) {
        return new PasswordHash(delegate.encode(rawPassword.getValue()));
    }

    @Override
    public boolean matches(RawPassword rawPassword, PasswordHash passwordHash) {
        if (rawPassword == null || passwordHash == null || passwordHash.getValue() == null) {
            return false;
        }
        if (looksLikeBcrypt(passwordHash.getValue())) {
            return delegate.matches(rawPassword.getValue(), passwordHash.getValue());
        }
        return Objects.equals(rawPassword.getValue(), passwordHash.getValue());
    }

    private boolean looksLikeBcrypt(String value) {
        return value.startsWith("$2a$") || value.startsWith("$2b$") || value.startsWith("$2y$");
    }
}

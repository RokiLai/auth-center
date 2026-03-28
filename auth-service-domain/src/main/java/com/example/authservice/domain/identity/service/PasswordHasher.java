package com.example.authservice.domain.identity.service;

import com.example.authservice.domain.identity.model.PasswordHash;
import com.example.authservice.domain.identity.model.RawPassword;

public interface PasswordHasher {

    PasswordHash encode(RawPassword rawPassword);

    boolean matches(RawPassword rawPassword, PasswordHash passwordHash);
}

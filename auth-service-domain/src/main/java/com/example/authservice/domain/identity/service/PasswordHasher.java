package com.example.authservice.domain.identity.service;

import com.example.authservice.domain.identity.model.valueobject.PasswordHash;
import com.example.authservice.domain.identity.model.valueobject.RawPassword;

public interface PasswordHasher {

    PasswordHash encode(RawPassword rawPassword);

    boolean matches(RawPassword rawPassword, PasswordHash passwordHash);
}

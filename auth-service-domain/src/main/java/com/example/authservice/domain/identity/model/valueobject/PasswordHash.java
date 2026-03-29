package com.example.authservice.domain.identity.model.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class PasswordHash {
    private final String value;

    public PasswordHash(String value) {
        this.value = value;
    }
}

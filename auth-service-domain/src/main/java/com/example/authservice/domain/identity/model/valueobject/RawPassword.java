package com.example.authservice.domain.identity.model.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class RawPassword {
    private final String value;

    public RawPassword(String value) {
        this.value = value;
    }
}

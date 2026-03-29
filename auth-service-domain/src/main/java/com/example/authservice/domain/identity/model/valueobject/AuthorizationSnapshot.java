package com.example.authservice.domain.identity.model.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AuthorizationSnapshot {
    private final List<String> roles;
    private final List<String> permissions;
}

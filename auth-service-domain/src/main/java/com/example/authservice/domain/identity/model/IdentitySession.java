package com.example.authservice.domain.identity.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class IdentitySession implements Serializable {
    private String sessionId;
    private Long accountId;
    private String username;
    private String token;
    private List<String> roles;
    private List<String> permissions;
}

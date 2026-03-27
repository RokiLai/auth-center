package com.example.authservice.auth;

import lombok.Data;

import java.util.List;

@Data
public class AccountInfo {
    private Long id;
    private String username;
    private String token;
    private List<String> role;
    private List<String> permissions;
}

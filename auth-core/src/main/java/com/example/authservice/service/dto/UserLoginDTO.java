package com.example.authservice.service.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLoginDTO {
    private Long id;
    private String username;
    private String email; // 新增字段
    private String token;

    public UserLoginDTO(Long id, String username, String email, String token) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.token = token;
    }
}
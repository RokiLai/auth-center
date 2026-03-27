package com.example.authservice.controller.response;

import lombok.Data;

@Data
public class UserInfoResponse {
    private Long id;
    private String username;
    private String email; // 新增字段
}

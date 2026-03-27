package com.example.authservice.infra.po;


import lombok.Data;

@Data
public class AccountPO {
    private Long id;
    private String username;
    private String password;
    private String email; // 新增字段
}

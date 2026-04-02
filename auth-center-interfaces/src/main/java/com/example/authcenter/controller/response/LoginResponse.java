package com.example.authcenter.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 登录接口的响应对象，仅用于接口层返回。
 * Response model for the login endpoint, used only at the interface layer.
 */
@Schema(name = "LoginResponse", description = "登录响应")
public record LoginResponse(
        @Schema(description = "用户 ID", example = "1")
        Long id,
        @Schema(description = "用户名", example = "admin")
        String username,
        @Schema(description = "邮箱地址", example = "admin@example.com")
        String email,
        @Schema(description = "访问令牌", example = "eyJhbGciOiJIUzI1NiJ9.xxx.yyy")
        String token
) {
}

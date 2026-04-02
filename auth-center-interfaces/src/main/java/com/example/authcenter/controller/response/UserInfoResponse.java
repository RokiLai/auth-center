package com.example.authcenter.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserInfoResponse", description = "用户信息响应")
public record UserInfoResponse(
        @Schema(description = "用户 ID", example = "1")
        Long id,
        @Schema(description = "用户名", example = "admin")
        String username,
        @Schema(description = "邮箱地址", example = "admin@example.com")
        String email
) {
}

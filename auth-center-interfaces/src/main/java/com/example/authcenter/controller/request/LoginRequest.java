package com.example.authcenter.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "LoginRequest", description = "登录请求")
public record LoginRequest(
        @Schema(
                description = "用户名",
                example = "admin",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minLength = 1
        )
        @NotBlank(message = "用户名不能为空")
        String username,

        @Schema(
                description = "密码",
                example = "admin123",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minLength = 1
        )
        @NotBlank(message = "密码不能为空")
        String password
) {
}

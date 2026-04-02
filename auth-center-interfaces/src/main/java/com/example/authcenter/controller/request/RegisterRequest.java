package com.example.authcenter.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "RegisterRequest", description = "注册请求")
public record RegisterRequest(
        @Schema(
                description = "用户名",
                example = "new-user",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minLength = 1
        )
        @NotBlank(message = "用户名不能为空")
        String username,

        @Schema(
                description = "密码，至少 6 位",
                example = "newPass123",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minLength = 6
        )
        @NotBlank(message = "密码不能为空")
        @Size(min = 6, message = "密码长度不能少于6位")
        String password,

        @Schema(
                description = "邮箱地址",
                example = "new-user@example.com",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minLength = 1,
                format = "email"
        )
        @NotBlank(message = "邮箱不能为空")
        @Email(message = "邮箱格式不正确")
        String email
) {
}

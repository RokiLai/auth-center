package com.example.authcenter.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "UpdatePasswordRequest", description = "修改密码请求")
public record UpdatePasswordRequest(
        @Schema(
                description = "旧密码",
                example = "oldPass123",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minLength = 1
        )
        @NotBlank(message = "旧密码不能为空")
        String oldPassword,

        @Schema(
                description = "新密码，至少 6 位",
                example = "newPass123",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minLength = 6
        )
        @NotBlank(message = "新密码不能为空")
        @Size(min = 6, message = "密码长度不能少于6位")
        String newPassword
) {
}

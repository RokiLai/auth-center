package com.example.authcenter.controller.response.doc;

import com.example.authcenter.controller.response.LoginResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginResultResponse", description = "登录成功响应")
public record LoginResultResponseDoc(
        @Schema(description = "业务状态码", example = "200")
        Integer code,
        @Schema(description = "响应消息", example = "success")
        String message,
        @Schema(description = "业务数据")
        LoginResponse data
) {
}

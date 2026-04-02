package com.example.authcenter.controller.response.doc;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiErrorResponse", description = "统一错误响应")
public record ApiErrorResponseDoc(
        @Schema(description = "业务状态码", example = "1001102")
        Integer code,
        @Schema(description = "错误消息", example = "缺少Token，请先登录")
        String message,
        @Schema(description = "错误响应的数据体，失败时通常为 null", nullable = true, example = "null")
        Object data
) {
}

package com.example.authcenter.controller.response.doc;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "BooleanResultResponse", description = "布尔型统一成功响应")
public record BooleanResultResponseDoc(
        @Schema(description = "业务状态码", example = "200")
        Integer code,
        @Schema(description = "响应消息", example = "success")
        String message,
        @Schema(description = "业务数据", example = "true")
        Boolean data
) {
}

package com.example.authcenter.config.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    private static final String BEARER_SCHEME = "bearerAuth";

    @Bean
    public OpenAPI authCenterOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Auth Center API")
                        .description("""
                                认证中心 HTTP 接口文档。
                                
                                鉴权说明：
                                1. 先调用登录接口获取 Token。
                                2. 受保护接口通过 `Authorization: Bearer <token>` 传递凭证。
                                3. `/auth/register` 与 `/auth/login` 无需登录。
                                """)
                        .version("v1")
                        .contact(new Contact().name("auth-center")))
                .servers(List.of(
                        new Server().url("/").description("当前环境"),
                        new Server().url("http://localhost:8080").description("本地默认地址")
                ))
                .components(new Components()
                        .addSecuritySchemes(BEARER_SCHEME, new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}

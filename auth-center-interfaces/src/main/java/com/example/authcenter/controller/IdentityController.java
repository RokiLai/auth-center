package com.example.authcenter.controller;

import com.example.authcenter.application.context.CurrentOperator;
import com.example.authcenter.annotation.AuthIdentity;
import com.example.authcenter.annotation.PassToken;
import com.example.authcenter.controller.request.LoginRequest;
import com.example.authcenter.controller.request.RegisterRequest;
import com.example.authcenter.controller.request.UpdatePasswordRequest;
import com.example.authcenter.controller.response.LoginResponse;
import com.example.authcenter.controller.response.doc.ApiErrorResponseDoc;
import com.example.authcenter.controller.response.doc.BooleanResultResponseDoc;
import com.example.authcenter.controller.response.doc.LoginResultResponseDoc;
import com.example.authcenter.identity.usecase.LoginUseCase;
import com.example.authcenter.identity.usecase.LogoutUseCase;
import com.example.authcenter.identity.usecase.RegisterUseCase;
import com.example.authcenter.identity.usecase.UpdatePasswordUseCase;
import com.example.authcenter.identity.usecase.command.LogoutCommand;
import com.example.authcenter.identity.usecase.command.RegisterCommand;
import com.example.authcenter.identity.usecase.command.UpdatePasswordCommand;
import com.example.authcenter.identity.usecase.result.LoginResult;
import com.roki.exception.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Identity", description = "认证中心身份接口")
@RequestMapping("/auth")
public class IdentityController {

    private static final String BEARER_PREFIX = "Bearer ";

    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;
    private final RegisterUseCase registerUseCase;
    private final UpdatePasswordUseCase updatePasswordUseCase;

    public IdentityController(LoginUseCase loginUseCase,
                              LogoutUseCase logoutUseCase,
                              RegisterUseCase registerUseCase,
                              UpdatePasswordUseCase updatePasswordUseCase) {
        this.loginUseCase = loginUseCase;
        this.logoutUseCase = logoutUseCase;
        this.registerUseCase = registerUseCase;
        this.updatePasswordUseCase = updatePasswordUseCase;
    }

    @PassToken
    @PostMapping("/register")
    @Operation(
            summary = "注册账号",
            description = "创建新的认证账号，用户名和邮箱需保持唯一。"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "注册成功",
                    content = @Content(schema = @Schema(implementation = BooleanResultResponseDoc.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "请求参数不合法，例如 `9001101 用户名不能为空`、`9001101 密码不能为空`",
                    content = @Content(
                            schema = @Schema(implementation = ApiErrorResponseDoc.class),
                            examples = @ExampleObject(
                                    name = "参数校验失败",
                                    value = "{\"code\":9001101,\"message\":\"用户名不能为空\",\"data\":null}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "用户名已存在",
                    content = @Content(
                            schema = @Schema(implementation = ApiErrorResponseDoc.class),
                            examples = @ExampleObject(
                                    name = "用户名已存在",
                                    value = "{\"code\":1001001,\"message\":\"用户名已存在\",\"data\":null}"
                            )
                    )
            )
    })
    public Result<Boolean> register(@Valid @RequestBody RegisterRequest request) {
        registerUseCase.register(new RegisterCommand(
                request.username(),
                request.password(),
                request.email()
        ));
        return Result.success(true);
    }

    @PassToken
    @PostMapping("/login")
    @Operation(
            summary = "账号登录",
            description = "使用用户名和密码登录，成功后会在响应头 `Authorization` 中返回 Bearer Token。"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "登录成功，响应头会返回 `Authorization: Bearer <token>`",
                    headers = {
                            @Header(
                                    name = "Authorization",
                                    description = "Bearer Token，格式为 `Bearer <token>`，供后续受保护接口使用",
                                    schema = @Schema(type = "string", example = "Bearer eyJhbGciOiJIUzI1NiJ9.xxx.yyy")
                            ),
                            @Header(
                                    name = "Access-Control-Expose-Headers",
                                    description = "跨域场景下暴露给前端读取的响应头列表",
                                    schema = @Schema(type = "string", example = "Authorization")
                            )
                    },
                    content = @Content(schema = @Schema(implementation = LoginResultResponseDoc.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "请求参数不合法，例如 `9001101 用户名不能为空`",
                    content = @Content(
                            schema = @Schema(implementation = ApiErrorResponseDoc.class),
                            examples = @ExampleObject(
                                    name = "参数校验失败",
                                    value = "{\"code\":9001101,\"message\":\"用户名不能为空\",\"data\":null}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "用户名或密码错误",
                    content = @Content(
                            schema = @Schema(implementation = ApiErrorResponseDoc.class),
                            examples = @ExampleObject(
                                    name = "登录失败",
                                    value = "{\"code\":1001101,\"message\":\"用户名或密码错误\",\"data\":null}"
                            )
                    )
            )
    })
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request,
                                       HttpServletResponse response) {
        LoginResult loginResult = loginUseCase.login(request.username(), request.password());
        LoginResponse loginResponse = new LoginResponse(
                loginResult.id(),
                loginResult.username(),
                loginResult.email(),
                loginResult.token()
        );
        response.setHeader("Authorization", BEARER_PREFIX + loginResponse.token());
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        return Result.success(loginResponse);
    }

    @PostMapping("/logout")
    @Operation(
            summary = "退出登录",
            description = "使当前 Bearer Token 对应的会话失效。",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "退出成功",
                    content = @Content(schema = @Schema(implementation = BooleanResultResponseDoc.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "未登录或 Token 无效，例如 `1001102 缺少Token，请先登录`、`1001103 Token已过期，请重新登录`、`1001104 Token无效`",
                    content = @Content(
                            schema = @Schema(implementation = ApiErrorResponseDoc.class),
                            examples = {
                                    @ExampleObject(
                                            name = "缺少 Token",
                                            value = "{\"code\":1001102,\"message\":\"缺少Token，请先登录\",\"data\":null}"
                                    ),
                                    @ExampleObject(
                                            name = "Token 已过期",
                                            value = "{\"code\":1001103,\"message\":\"Token已过期，请重新登录\",\"data\":null}"
                                    ),
                                    @ExampleObject(
                                            name = "Token 无效",
                                            value = "{\"code\":1001104,\"message\":\"Token无效\",\"data\":null}"
                                    )
                            }
                    )
            )
    })
    // 当前登录身份由接口层注入，再显式传入应用用例。
    // The current authenticated identity is injected at the interface layer and then passed explicitly to the use case.
    public Result<Boolean> logout(
            @Parameter(hidden = true) @AuthIdentity CurrentOperator currentOperator) {
        return Result.success(logoutUseCase.logout(new LogoutCommand(currentOperator)));
    }

    @PostMapping("/update-password")
    @Operation(
            summary = "修改密码",
            description = "校验旧密码后更新当前登录账号的新密码。",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "修改成功",
                    content = @Content(schema = @Schema(implementation = BooleanResultResponseDoc.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "请求参数不合法，例如 `9001101 新密码不能为空`",
                    content = @Content(
                            schema = @Schema(implementation = ApiErrorResponseDoc.class),
                            examples = @ExampleObject(
                                    name = "参数校验失败",
                                    value = "{\"code\":9001101,\"message\":\"新密码不能为空\",\"data\":null}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "未登录、Token 无效或旧密码错误",
                    content = @Content(
                            schema = @Schema(implementation = ApiErrorResponseDoc.class),
                            examples = {
                                    @ExampleObject(
                                            name = "旧密码错误",
                                            value = "{\"code\":1001005,\"message\":\"旧密码错误\",\"data\":null}"
                                    ),
                                    @ExampleObject(
                                            name = "Token 缺失",
                                            value = "{\"code\":1001102,\"message\":\"缺少Token，请先登录\",\"data\":null}"
                                    ),
                                    @ExampleObject(
                                            name = "Token 无效",
                                            value = "{\"code\":1001104,\"message\":\"Token无效\",\"data\":null}"
                                    )
                            }
                    )
            )
    })
    // 改密属于身份能力，由 identity 控制器承接认证上下文并转给应用用例。
    // Password updates belong to the identity capability, so the identity controller maps authenticated context into the use case command.
    public Result<Boolean> updatePassword(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "修改密码请求体",
                    content = @Content(
                            schema = @Schema(implementation = UpdatePasswordRequest.class),
                            examples = @ExampleObject(
                                    name = "默认示例",
                                    value = "{\"oldPassword\":\"oldPass123\",\"newPassword\":\"newPass123\"}"
                            )
                    )
            )
            @Valid @RequestBody UpdatePasswordRequest request,
            @Parameter(hidden = true) @AuthIdentity CurrentOperator currentOperator) {
        updatePasswordUseCase.updatePassword(new UpdatePasswordCommand(
                currentOperator,
                request.oldPassword(),
                request.newPassword()
        ));
        return Result.success(true);
    }
}

package com.example.authservice.controller;

import com.example.authservice.annotation.PassToken;
import com.example.authservice.controller.request.LoginRequest;
import com.example.authservice.identity.dto.LoginResult;
import com.example.authservice.identity.usecase.LoginUseCase;
import com.example.authservice.identity.usecase.LogoutUseCase;
import com.example.authservice.service.dto.UserLoginDTO;
import com.roki.exception.result.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class IdentityController {

    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private LoginUseCase loginUseCase;

    @Autowired
    private LogoutUseCase logoutUseCase;

    @PassToken
    @PostMapping("/login")
    public Result<UserLoginDTO> login(@RequestBody LoginRequest request,
                                      HttpServletResponse response) {
        LoginResult loginResult = loginUseCase.login(request.getUsername(), request.getPassword());
        UserLoginDTO userLoginDTO = new UserLoginDTO(
                loginResult.getId(),
                loginResult.getUsername(),
                loginResult.getEmail(),
                loginResult.getToken()
        );
        response.setHeader("Authorization", BEARER_PREFIX + userLoginDTO.getToken());
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        return Result.success(userLoginDTO);
    }

    @PostMapping("/logout")
    public Result<Boolean> logout() {
        return Result.success(logoutUseCase.logout());
    }
}

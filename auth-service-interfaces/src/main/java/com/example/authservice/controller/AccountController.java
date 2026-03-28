package com.example.authservice.controller;

import com.example.authservice.annotation.PassToken;
import com.example.authservice.controller.request.RegisterRequest;
import com.example.authservice.controller.request.UpdatePasswordRequest;
import com.example.authservice.service.AccountService;
import com.roki.exception.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AccountController {
    @Autowired
    AccountService accountService;

    @PassToken
    @PostMapping("/register")
    public Result<Boolean> register(@RequestBody RegisterRequest request) {
        accountService.register(request.getUsername(), request.getPassword(), request.getEmail(), null);
        return Result.success(true);
    }

    @PostMapping("/update-password")
    public Result<Boolean> updatePassword(@RequestBody UpdatePasswordRequest request) {
        accountService.updatePassword(request.getOldPassword(), request.getNewPassword());
        return Result.success(true);
    }
}

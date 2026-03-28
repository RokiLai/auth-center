package com.example.authservice.identity.usecase;

import com.example.authservice.identity.dto.LoginResult;

public interface LoginUseCase {

    LoginResult login(String username, String password);
}

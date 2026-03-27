package com.example.authservice.config;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.authservice.controller.response.CommonResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // 捕获所有未处理的异常
    @ExceptionHandler(Exception.class)
    public CommonResponse<Object> handleException(Exception e, HttpServletRequest request) {
        e.printStackTrace(); // 打印异常日志，便于调试
        return CommonResponse.failure(500, "服务器内部错误: " + e.getMessage());
    }

    // 捕获自定义的 RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public CommonResponse<Object> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        e.printStackTrace(); // 打印异常日志
        return CommonResponse.failure(500, "运行时错误: " + e.getMessage());
    }

    // 捕获 401 未授权异常
    @ExceptionHandler(org.springframework.web.server.ResponseStatusException.class)
    public CommonResponse<Object> handleUnauthorizedException(org.springframework.web.server.ResponseStatusException e) {
        if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return CommonResponse.failure(401, "未授权: " + e.getReason());
        }
        return CommonResponse.failure(400, "错误: " + e.getReason());
    }
}
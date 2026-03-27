package com.example.authservice.config;

import com.example.authservice.annotation.PassToken;
import com.example.authservice.auth.AccountContextHolder;
import com.example.authservice.auth.AccountInfo;
import com.example.authservice.exception.AuthErrorCode;
import com.example.authservice.service.AccountService;
import com.example.authservice.util.JwtUtil;
import com.roki.exception.BusinessException;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.Objects;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class); // 添加日志记录器

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AccountService accountService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            logger.info("非控制器请求，直接放行");
            return true; // 放行非控制器请求
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 跳过 @PassToken 标记的接口
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                logger.info("接口 {} 被 @PassToken 标记，跳过验证", method.getName());
                return true;
            }
        }

        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            logger.warn("请求缺少 Token，拒绝访问");
            throw new BusinessException(AuthErrorCode.TOKEN_MISSING);
        }

        try {
            String username = jwtUtil.parseUsername(token);
            logger.info("Token 验证通过，用户名: {}", username);
            AccountInfo accountInfo = accountService.getAccountInfo(username);
            if (accountInfo == null || !Objects.equals(accountInfo.getToken(), token)) {
                logger.warn("Token 已过期");
                throw new BusinessException(AuthErrorCode.TOKEN_EXPIRED);
            }
            request.setAttribute("username", username);
            AccountContextHolder.set(accountInfo);
        } catch (JwtException e) {
            logger.error("Token 验证失败: {}", e.getMessage());
            throw new BusinessException(AuthErrorCode.TOKEN_INVALID);
        }

        return true;
    }
}

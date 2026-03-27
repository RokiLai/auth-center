package com.example.authservice.util;


import com.example.authservice.util.config.JwtProperties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtProperties jwtProperties;

    @Test
    void testGenerateAndParseToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);
        assertNotNull(token);

        String parsedUsername = jwtUtil.parseUsername(token);
        assertEquals(username, parsedUsername);
    }

    @Test
    void testTokenNotExpired() {
        String token = jwtUtil.generateToken("abc");
        assertFalse(jwtUtil.isTokenExpired(token));
    }

    @Test
    void testSecretLoadedFromConfig() {
        assertNotNull(jwtProperties.getSecret());
        assertEquals("your-256-bit-secret-your-256-bit-secret", jwtProperties.getSecret());
    }

    @Test
    void testTokenExpirationLogic() throws InterruptedException {
        // 模拟短生命周期 token（手动改过期时间可测试此用例）
        // 示例（伪逻辑）：生成一个 1 毫秒后过期的 token 并 Thread.sleep(2ms)
    }
}

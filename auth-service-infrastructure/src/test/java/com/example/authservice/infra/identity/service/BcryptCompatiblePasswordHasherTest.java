package com.example.authservice.infra.identity.service;

import com.example.authservice.domain.identity.model.PasswordHash;
import com.example.authservice.domain.identity.model.RawPassword;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BcryptCompatiblePasswordHasherTest {

    private final BcryptCompatiblePasswordHasher passwordHasher = new BcryptCompatiblePasswordHasher();

    @Test
    void shouldEncodeNewPasswordAsBcrypt() {
        PasswordHash encoded = passwordHasher.encode(new RawPassword("123456"));

        assertThat(encoded.getValue()).startsWith("$2");
        assertThat(passwordHasher.matches(new RawPassword("123456"), encoded)).isTrue();
        assertThat(passwordHasher.matches(new RawPassword("654321"), encoded)).isFalse();
    }

    @Test
    void shouldStillMatchLegacyPlaintextPassword() {
        PasswordHash legacy = new PasswordHash("123456");

        assertThat(passwordHasher.matches(new RawPassword("123456"), legacy)).isTrue();
        assertThat(passwordHasher.matches(new RawPassword("654321"), legacy)).isFalse();
    }
}

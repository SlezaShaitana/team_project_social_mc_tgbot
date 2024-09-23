package com.socialNetwork.TgBot.service;

import com.socialNetwork.TgBot.client.AuthClient;
import com.socialNetwork.TgBot.dto.authDto.AuthenticateDto;
import com.socialNetwork.TgBot.dto.authDto.AuthenticateResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final RedisTemplate<String, String> redisTemplate;

    private final AuthClient authClient;

    public boolean login(Long userId, String email, String password) {
        log.info("AuthServer: login: Попытка входа в аккаунт: {}, {}, {}", userId, email, password);
        AuthenticateDto authenticateDto = new AuthenticateDto();
        authenticateDto.setEmail(email);
        authenticateDto.setPassword(password);

        AuthenticateResponseDto responseDto = null;
        try {
            responseDto = authClient.login(authenticateDto);
            String accessToken = responseDto.getAccessToken();
            setToken(userId, accessToken);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return responseDto != null;
    }

    private void setToken(Long userId, String accessToken) {
        if (getToken(userId) != null) {
            redisTemplate.delete(String.valueOf(userId));
        }
        redisTemplate.opsForValue().set(String.valueOf(userId), accessToken);
    }

    public String getToken(Long userId) {
        return redisTemplate.opsForValue().get(String.valueOf(userId));
    }

}

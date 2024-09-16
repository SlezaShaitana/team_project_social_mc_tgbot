package com.socialNetwork.TgBot.service;

import com.socialNetwork.TgBot.client.AuthClient;
import com.socialNetwork.TgBot.dto.AuthenticateDto;
import com.socialNetwork.TgBot.dto.AuthenticateResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AuthClient authClient;

    public boolean login(String email, String password) {
        log.info("AuthServer: login: Попытка входа в аккаунт: {}, {}", email, password);
        AuthenticateDto authenticateDto = new AuthenticateDto();
        authenticateDto.setEmail(email);
        authenticateDto.setPassword(password);
        AuthenticateResponseDto responseDto = null;
        try {
            responseDto = authClient.login(authenticateDto);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return responseDto != null;
    }


}

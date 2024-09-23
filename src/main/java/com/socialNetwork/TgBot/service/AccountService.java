package com.socialNetwork.TgBot.service;


import com.socialNetwork.TgBot.client.AccountClient;
import com.socialNetwork.TgBot.client.AuthClient;

import com.socialNetwork.TgBot.dto.accountDto.AccountMeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {

    private final AccountClient accountClient;

    private final AuthService authService;

    public void deleteAccount(Long userId)
    {
        String userToken = "Bearer ".concat(authService.getToken(userId));
        accountClient.deleteMyAccountById(userToken);
    }
}

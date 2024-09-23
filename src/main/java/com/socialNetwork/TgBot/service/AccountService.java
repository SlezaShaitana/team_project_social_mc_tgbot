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


    public void deleteAccount(String userToken)
    {
        accountClient.deleteMyAccountById(userToken);
    }
}

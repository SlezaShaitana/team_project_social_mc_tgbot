package com.socialNetwork.TgBot.service;

import com.socialNetwork.TgBot.dto.TokenBot;
import com.socialNetwork.TgBot.repository.TokenBotStorage;
import lombok.RequiredArgsConstructor;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TokenBotService {


    private final TokenBotStorage tokenBotStorage;

    public void saveUserToken(String userToken)
    {
        TokenBot tokenBot = new TokenBot();
        tokenBot.setToken(userToken);
        tokenBotStorage.save(tokenBot);
    }


    public String getUserIdByToken(String userToken)
    {
        Optional<TokenBot> userTokenFound = tokenBotStorage.findById(userToken);
        return userTokenFound.map(TokenBot::getToken).orElse(null);
    }



}
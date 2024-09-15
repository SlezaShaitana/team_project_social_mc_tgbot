package com.socialNetwork.TgBot.config;

import com.socialNetwork.TgBot.bot.NetworkBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@Slf4j
public class TelegramBotConfig {

    @Bean
    TelegramBotsApi telegramBotsApi(NetworkBot networkBot) {
        TelegramBotsApi botsApi = null;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(networkBot);
        } catch (TelegramApiException e) {
            log.error("Error occurred while sending message to telegram!", e);
        }
        return botsApi;
    }
}

package com.socialNetwork.TgBot.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Slf4j
public class StartCommand implements IBotCommand {


    @Override
    public String getCommandIdentifier() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Starts the TgBot";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("""
        Приветствуем тебя в мобильной версии Code Lounge!
        Для начала, войди в свой аккаунт.
        Вызови команду /login {email} {password}
        """);
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error occurred in /start command", e);
        }
    }
}

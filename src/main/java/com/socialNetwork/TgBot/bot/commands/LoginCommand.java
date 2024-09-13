package com.socialNetwork.TgBot.bot.commands;

import com.socialNetwork.TgBot.client.AuthClient;
import com.socialNetwork.TgBot.dto.AuthenticateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Service
@RequiredArgsConstructor
public class LoginCommand implements IBotCommand {

    private final AuthClient authClient;

    @Override
    public String getCommandIdentifier() {
        return "login";
    }

    @Override
    public String getDescription() {
        return "changes_role_of_the_user";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        AuthenticateDto authenticateDto = new AuthenticateDto();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        authenticateDto.setEmail(strings[0]);
        authenticateDto.setPassword(strings[1]);
        authClient.login(authenticateDto);
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

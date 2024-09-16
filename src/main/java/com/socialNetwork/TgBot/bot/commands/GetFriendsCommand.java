package com.socialNetwork.TgBot.bot.commands;

import com.socialNetwork.TgBot.service.AuthService;
import com.socialNetwork.TgBot.service.FriendsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetFriendsCommand implements IBotCommand {

    private final AuthService authService;

    private final FriendsService friendsService;

    @Override
    public String getCommandIdentifier() {
        return "getFriends";
    }

    @Override
    public String getDescription() {
        return "Get list of friends";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        Long userId = message.getFrom().getId();

        if (authService.getToken(userId).isEmpty()) {
            log.info("GetFriendsCommand: Ошибка входа. Токен не найден или время жизни токена истекло.");
            sendMessage.setText("Время сессии закончилось. Пожалуйста перезайдите в аккаунт.");
        } else {
            sendMessage.setText(friendsService.getFriends(userId));
        }

        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}

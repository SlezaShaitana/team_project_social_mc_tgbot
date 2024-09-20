package com.socialNetwork.TgBot.bot.commands;

import com.socialNetwork.TgBot.service.AuthService;
import com.socialNetwork.TgBot.service.FriendsService;
import com.socialNetwork.TgBot.service.PostService;
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
public class GetPostsCommand implements IBotCommand {

    private final AuthService authService;

    private final PostService postService;

    @Override
    public String getCommandIdentifier() {
        return "getPosts";
    }

    @Override
    public String getDescription() {
        return "Get list user's posts";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        Long userId = message.getFrom().getId();

        if (authService.getToken(userId).isEmpty()) {
            log.info("GetPostsCommand: Ошибка входа. Токен не найден или время жизни токена истекло.");
            sendMessage.setText("Время сессии закончилось. Пожалуйста перезайдите в аккаунт.");
        } else {
            sendMessage.setText(postService.getPosts(userId));
        }

        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}

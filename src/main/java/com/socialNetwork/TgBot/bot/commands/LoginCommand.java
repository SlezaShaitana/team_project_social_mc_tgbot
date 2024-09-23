package com.socialNetwork.TgBot.bot.commands;

import com.socialNetwork.TgBot.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Service
@RequiredArgsConstructor
@Slf4j
public class LoginCommand implements IBotCommand {

    private final AuthService authService;

    @Override
    public String getCommandIdentifier() {
        return "login";
    }

    @Override
    public String getDescription() {
        return "Login user";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());

        if (authService.login(message.getFrom().getId(), strings[0], strings[1])) {
            sendMessage.setText("""
                    Вы успешно вошли в аккаунт!
                    Вам доступен список команд:
                    /getFriends - получение списка друзей
                    /getPosts - получение списка постов
                    /delete - удаление аккаунт
                    """);
            log.info("Пользователь успешно вошел в аккаунт");
        } else {
            sendMessage.setText("Пароль или логин неверный! \nПопробуйте снова");
            log.info("Пользователь не прошел аутентификацию");
        }
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}

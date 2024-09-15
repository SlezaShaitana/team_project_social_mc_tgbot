package com.socialNetwork.TgBot.bot.commands;

import com.socialNetwork.TgBot.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Service
@RequiredArgsConstructor
public class LoginCommand implements IBotCommand {

    private static final Logger log = LoggerFactory.getLogger(LoginCommand.class);
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

        if (authService.login(strings[0], strings[1])) {
            sendMessage.setText("Вы успешно вошли в аккаунт!");
            log.info("Пользователь успешно вошел в аккаунт");
        } else {
            sendMessage.setText("Пароль или логин неверный! \nПопробуйте снова");
            log.info("Пользователь не прошел аутенфикацию");
        }
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

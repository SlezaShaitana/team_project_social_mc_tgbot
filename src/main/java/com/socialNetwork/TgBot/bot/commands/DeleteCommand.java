package com.socialNetwork.TgBot.bot.commands;

import com.socialNetwork.TgBot.service.AccountService;
import com.socialNetwork.TgBot.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteCommand implements IBotCommand {


    private final AccountService accountService;

    private final AuthService authService;

    @Override
    public String getCommandIdentifier() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "Delete account";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        Long userId = message.getFrom().getId();
        if(!authService.getToken(userId).isEmpty()) {
            accountService.deleteAccount(userId);
            sendMessage.setText("account deleted");
        }else {
            sendMessage.setText("such account does not exist to be deleted!");
        }
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}


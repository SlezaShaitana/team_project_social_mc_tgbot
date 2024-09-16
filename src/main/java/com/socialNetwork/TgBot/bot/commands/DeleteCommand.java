package com.socialNetwork.TgBot.bot.commands;

//import com.socialNetwork.TgBot.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteCommand implements IBotCommand {


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
//        accountService.deleteAccount(sendMessage.getChatId());
        sendMessage.setText("account deleted");
        try {
            sendMessage.setText("account deleted");
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.socialNetwork.TgBot.bot.commands;

import com.socialNetwork.TgBot.entity.Role;
import com.socialNetwork.TgBot.entity.User;
import com.socialNetwork.TgBot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateRoleCommand implements IBotCommand {

    private final UserRepository userRepository;

    @Override
    public String getCommandIdentifier() {
        return "change role";
    }

    @Override
    public String getDescription() {
        return "changes role of the user";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("please enter your email)");
        Optional<User> user = userRepository.findByEmail(strings[0]);
        if (user.isPresent()) {
            User userFound = user.get();
            userFound.setRoles(List.of(Role.valueOf(strings[1])));
            userRepository.save(userFound);
        }
        sendMessage.setText("User with this email is not found");
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

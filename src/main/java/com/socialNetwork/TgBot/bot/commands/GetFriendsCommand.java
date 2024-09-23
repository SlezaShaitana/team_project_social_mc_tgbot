package com.socialNetwork.TgBot.bot.commands;

import com.socialNetwork.TgBot.dto.firendDto.FriendDto;
import com.socialNetwork.TgBot.service.AuthService;
import com.socialNetwork.TgBot.service.FriendsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

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
            sendMessage(absSender, sendMessage);
        } else {
            List<FriendDto> friendList = friendsService.getFriends(userId);
            if (friendList.isEmpty()) {
                log.info("Список друзей пуст");

                sendMessage.setText("Список ваших друзей пуст!");
                sendMessage(absSender, sendMessage);
            } else {
                log.info("Начало отправления друзей");

                sendMessage.setText("Список ваших друзей: ");
                sendMessage(absSender, sendMessage);
                for (FriendDto friendDto : friendList) {
                    SendPhoto sendPhoto = new SendPhoto();
                    sendPhoto.setChatId(message.getChatId().toString());
                    if (friendDto.getPhotoUrl() != null) {
                        try {
                            sendPhoto.setPhoto(new InputFile(downloadImage(friendDto.getPhotoUrl())));
                            sendPhoto.setCaption(friendDto.getFirstName() + " " + friendDto.getLastName());
                        } catch (IOException e) {
                            log.error("Error while downloading image from {}", friendDto.getPhotoUrl(), e);
                        }
                    } else {
                        sendPhoto.setPhoto(new InputFile(new File("photo/img.png")));
                        sendPhoto.setCaption(friendDto.getFirstName() + " " + friendDto.getLastName());
                    }
                    sendPhoto(absSender, sendPhoto);
                }
            }
        }
    }

    private void sendMessage(AbsSender absSender, SendMessage sendMessage) {
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void sendPhoto(AbsSender absSender, SendPhoto sendPhoto) {
        try {
            absSender.execute(sendPhoto);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private static File downloadImage(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        File file;
        try (InputStream inputStream = url.openStream()) {
            String[] parts = imageUrl.split("/");
            String fileName = parts[parts.length - 1];
            file = new File("photo/" + fileName);

            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        }

        return file;
    }
}

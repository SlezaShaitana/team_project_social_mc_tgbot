package com.socialNetwork.TgBot.bot.commands;

import com.socialNetwork.TgBot.dto.firendDto.FriendDto;
import com.socialNetwork.TgBot.dto.postDto.PostDto;
import com.socialNetwork.TgBot.service.AuthService;
import com.socialNetwork.TgBot.service.FriendsService;
import com.socialNetwork.TgBot.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
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
            sendMessage(absSender, sendMessage);
        } else {
            List<PostDto> posts = postService.getPosts(userId);
            if (posts.isEmpty()) {
                log.info("Список друзей пуст");

                sendMessage.setText("Список ваших постов пуст!");
                sendMessage(absSender, sendMessage);
            } else {
                sendMessage.setText("Список ваших постов: ");
                sendMessage(absSender, sendMessage);
                int numberPost = 1;
                for (PostDto post : posts) {
                    SendPhoto sendPhoto = new SendPhoto();
                    sendPhoto.setChatId(message.getChatId().toString());
                    if (!post.getImagePath().isEmpty()) {
                        try {
                            sendMessage.setText("Пост " + numberPost++ + ":" + "\n"
                                    + "  "+ post.getTitle());
                            sendMessage(absSender, sendMessage);
                            sendPhoto.setPhoto(new InputFile(downloadImage(post.getImagePath())));
                            sendPhoto.setCaption(Jsoup.parse(post.getPostText()).text());
                            sendPhoto(absSender, sendPhoto);
                        } catch (IOException e) {
                            log.error("Error while downloading image from {}", post.getImagePath(), e);
                        }
                    } else {
                        String response = "Пост " + numberPost++ + ":" + "\n"
                                + "  " + post.getTitle() + "\n"
                                + "  " + Jsoup.parse(post.getPostText()).text();
                        sendMessage.setText(response);
                        sendMessage(absSender, sendMessage);
                    }
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

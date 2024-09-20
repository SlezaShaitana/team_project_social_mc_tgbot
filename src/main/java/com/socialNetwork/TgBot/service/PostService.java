package com.socialNetwork.TgBot.service;

import com.socialNetwork.TgBot.client.PostClient;
import com.socialNetwork.TgBot.dto.postDto.PostDto;
import com.socialNetwork.TgBot.dto.postDto.TypePost;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final AuthService authService;

    private final PostClient postsClient;


    public String getPosts(Long userId) {
        log.info("PostsService: getPosts: Получение постов аккаунта: {}", userId);

        String token = "Bearer ".concat(authService.getToken(userId));
        List<PostDto> posts = postsClient.getPosts(token);

        StringBuilder sb = new StringBuilder();
        sb.append("Список ваших постов:").append("\n");
        int numberPost = 1;
        for (PostDto post : posts) {
            sb.append(" " + numberPost++ + ")").append("\n").append("  Заголовок: " + post.getTitle() + "\n")
                    .append("  Количество комментариев: " + post.getCommentsCount() + "\n")
                    .append("  Количество лайков: " + post.getLikeAmount() + "\n");
            if (post.getType().equals(TypePost.POSTED)){
                sb.append("  Тип: Опубликованный");
            }
            if (post.getType().equals(TypePost.QUEUED)){
                sb.append("  Тип: Отложенный").append("  Дата и время публикации: " + post.getPublishDate());
            }
        }
        return sb.toString();
    }
}

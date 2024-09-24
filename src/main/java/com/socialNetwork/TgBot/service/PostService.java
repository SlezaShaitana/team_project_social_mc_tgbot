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


    public List<PostDto> getPosts(Long userId) {
        log.info("PostsService: getPosts: Получение постов аккаунта: {}", userId);

        try {
            String token = "Bearer ".concat(authService.getToken(userId));
            String accountId = DecodedToken.getDecoded(token).getId();
            List<PostDto> posts = postsClient.getPosts(token, accountId, 1000000000).toList();
            log.info("Запрос к mc-post выполнен успешно! Список постов получен");
            return posts;
        }catch (Exception e){
            log.error("Результат получения постов: Error: {}", e.getMessage());
            return List.of();
        }
    }
}

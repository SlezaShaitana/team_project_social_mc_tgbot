package com.socialNetwork.TgBot.client;

import com.socialNetwork.TgBot.dto.postDto.PostDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.UUID;

@FeignClient(value = "posts", url = "http://79.174.80.200:8091/api/v1")
public interface PostClient {

    @GetMapping("/post_by_authorId")
    List<PostDto> getPosts(@RequestHeader("Authorization") String bearerToken);
}

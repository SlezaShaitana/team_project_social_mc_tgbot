package com.socialNetwork.TgBot.client;

import com.socialNetwork.TgBot.dto.postDto.PostDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "posts", url = "http://79.174.80.200:8091/api/v1")
public interface PostClient {

    @GetMapping("/post")
    Page<PostDto> getPosts(@RequestHeader("Authorization") String bearerToken,
                           @RequestParam String accountIds,
                           @RequestParam Integer size);
}

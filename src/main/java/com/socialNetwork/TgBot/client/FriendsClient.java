package com.socialNetwork.TgBot.client;

import com.socialNetwork.TgBot.dto.FriendShortDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "friends", url = "http://79.174.80.200:8090/api/v1/friends")
public interface FriendsClient {

    @GetMapping
    Page<FriendShortDto> getFriendList(@RequestHeader("Authorization") String bearerToken);
}

package com.socialNetwork.TgBot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.UUID;

@FeignClient(value = "friends", url = "http://79.174.80.200:8090/api/v1/friends")
public interface FriendsClient {

    @GetMapping("/friendId")
    List<UUID> getFriendList(@RequestHeader("Authorization") String bearerToken);
}

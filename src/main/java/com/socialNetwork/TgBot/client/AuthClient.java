package com.socialNetwork.TgBot.client;

import org.springframework.cloud.openfeign.FeignClient;
import com.socialNetwork.TgBot.entity.User;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "email",
        url = "http://79.174.80.200:8086/api/v1/auth/login")
public interface AuthClient {


    @GetMapping
     User getByEmail();
}

package com.socialNetwork.TgBot.client;

import com.socialNetwork.TgBot.dto.authDto.AuthenticateDto;
import com.socialNetwork.TgBot.dto.authDto.AuthenticateResponseDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "auth", url = "http://79.174.80.200:8086/api/v1/auth")
public interface AuthClient {

    @PostMapping("/login")
    AuthenticateResponseDto login(@RequestBody @Valid AuthenticateDto authenticateDto);

}

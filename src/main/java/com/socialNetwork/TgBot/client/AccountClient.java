package com.socialNetwork.TgBot.client;

import com.socialNetwork.TgBot.dto.accountDto.AccountPageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(value = "account", url = "http://79.174.80.200:8085/api/v1/account")
public interface AccountClient {

    @GetMapping("/search")
    AccountPageDTO getListAccounts(@RequestHeader("Authorization") String bearerToken,
                                   @RequestParam(required = false) List<UUID> ids);
}

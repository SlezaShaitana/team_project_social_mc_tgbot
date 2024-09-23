package com.socialNetwork.TgBot.client;

import com.socialNetwork.TgBot.dto.accountDto.AccountMeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(value = "account", url = "http://79.174.80.200:8085/api/v1/account")
public interface AccountClient {

    @GetMapping("/{id}")
    AccountMeDTO getAccount(@RequestHeader("Authorization") String bearerToken,
                            @PathVariable UUID id);



  @DeleteMapping("/me")
  void deleteMyAccountById(@RequestHeader("Authorization") String authorization);


}

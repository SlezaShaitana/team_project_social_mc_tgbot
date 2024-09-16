package com.socialNetwork.TgBot.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor
@Data
@NoArgsConstructor
@RedisHash("TokenBot")
public class TokenBot {
    String token;
}

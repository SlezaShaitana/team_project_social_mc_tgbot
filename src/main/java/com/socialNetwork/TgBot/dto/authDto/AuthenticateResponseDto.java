package com.socialNetwork.TgBot.dto.authDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticateResponseDto {
    private String accessToken;
    private String refreshToken;
}


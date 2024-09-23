package com.socialNetwork.TgBot.dto.firendDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendDto {

    private String firstName;

    private String lastName;

    private String photoUrl;
}

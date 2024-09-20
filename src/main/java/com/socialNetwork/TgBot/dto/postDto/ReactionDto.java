package com.socialNetwork.TgBot.dto.postDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReactionDto {

    private String reactionType;

    private Integer count;
}

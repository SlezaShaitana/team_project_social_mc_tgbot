package com.socialNetwork.TgBot.dto.postDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDto {

    private String id;

    private Boolean isDeleted;

    private String name;
}

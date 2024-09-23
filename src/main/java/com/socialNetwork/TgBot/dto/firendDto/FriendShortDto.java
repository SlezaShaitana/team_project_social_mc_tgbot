package com.socialNetwork.TgBot.dto.firendDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendShortDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("deleted")
    private boolean isDeleted;

    @JsonProperty("statusCode")
    private String statusCode;

    @JsonProperty("friendId")
    private String friendId;

    @JsonProperty("previousStatusCode")
    private String previousStatusCode;

    @JsonProperty("rating")
    private int rating;
}
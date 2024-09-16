package com.socialNetwork.TgBot.dto.accountDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageableDTO {

    private SortDTO sortDTO;

    private int pageNumber;

    private boolean unpaged;

    private boolean paged;

    private int pageSize;

    private int offset;
}
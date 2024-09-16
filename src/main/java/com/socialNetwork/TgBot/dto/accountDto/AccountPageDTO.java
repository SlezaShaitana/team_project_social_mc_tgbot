package com.socialNetwork.TgBot.dto.accountDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountPageDTO {
    private long totalElements;
    private int totalPages;
    private SortDTO sortDTO;
    private int numberOfElements;
    private PageableDTO pageable;
    private boolean first;
    private boolean last;
    private int size;
    private List<AccountMeDTO> content;
    private int number;
    private boolean empty;
}

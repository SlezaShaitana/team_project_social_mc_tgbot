package com.socialNetwork.TgBot.service;

import com.socialNetwork.TgBot.client.AccountClient;
import com.socialNetwork.TgBot.client.FriendsClient;
import com.socialNetwork.TgBot.dto.FriendShortDto;
import com.socialNetwork.TgBot.dto.accountDto.AccountMeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendsService {

    private final AuthService authService;

    private final FriendsClient friendsClient;

    private final AccountClient accountClient;

    public String getFriends(Long userId) {
        log.info("FriendsService: getFriends: Получение друзей: {}", userId);

        String token = authService.getToken(userId);
        List<UUID> friendsIds = getFriendsFromService(token);
        return getAccountsFromService(token, friendsIds);
    }

    private List<UUID> getFriendsFromService(String token) {
        log.info("FriendsService: getFriendsFromService: Получение друзей из микросервиса: {}", token);

        Page<FriendShortDto> friendShortDtoPage = friendsClient.getFriendList(token);
        return friendShortDtoPage.stream().map(f -> UUID.fromString(f.getId())).toList();
    }

    private String getAccountsFromService(String token, List<UUID> friendsIds) {
        log.info("FriendsService: getAccountsFromService: Получение аккаунтов друзей из микросервиса: {}, {}", token, friendsIds);

        List<AccountMeDTO> accounts = accountClient.getListAccounts(token, friendsIds).getContent();
        StringBuilder sb = new StringBuilder();
        sb.append("Список ваших друзей:");
        for (AccountMeDTO account : accounts) {
            sb.append(account.getFirstName()).append(" ").append(account.getLastName());
        }
        return sb.toString();
    }
}

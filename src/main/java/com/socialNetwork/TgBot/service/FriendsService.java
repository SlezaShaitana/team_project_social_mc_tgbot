package com.socialNetwork.TgBot.service;

import com.socialNetwork.TgBot.client.AccountClient;
import com.socialNetwork.TgBot.client.FriendsClient;
import com.socialNetwork.TgBot.dto.accountDto.AccountMeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        String token = "Bearer ".concat(authService.getToken(userId));
        List<UUID> friendsIds = friendsClient.getFriendList(token);
        return getAccountsFromService(token, friendsIds);
    }

    private String getAccountsFromService(String token, List<UUID> friendsIds) {
        log.info("FriendsService: getAccountsFromService: Получение аккаунтов друзей из микросервиса: {}, {}", token, friendsIds);

        StringBuilder sb = new StringBuilder();
        sb.append("Список ваших друзей:").append("\n");
        for (UUID id : friendsIds) {
            AccountMeDTO account = accountClient.getAccount(token, id);
            sb.append(account.getFirstName()).append(" ").append(account.getLastName()).append("\n");
        }
        return sb.toString();
    }
}

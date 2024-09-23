package com.socialNetwork.TgBot.service;

import com.socialNetwork.TgBot.client.AccountClient;
import com.socialNetwork.TgBot.client.FriendsClient;
import com.socialNetwork.TgBot.dto.accountDto.AccountMeDTO;
import com.socialNetwork.TgBot.dto.firendDto.FriendDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendsService {

    private final AuthService authService;

    private final FriendsClient friendsClient;

    private final AccountClient accountClient;

    public List<FriendDto> getFriends(Long userId) {
        log.info("FriendsService: getFriends: Получение друзей: {}", userId);

        String token = "Bearer ".concat(authService.getToken(userId));
        List<UUID> friendsIds = friendsClient.getFriendList(token);
        return getAccountsFromService(token, friendsIds);
    }

    private List<FriendDto> getAccountsFromService(String token, List<UUID> friendsIds) {
        log.info("FriendsService: getAccountsFromService: Получение аккаунтов друзей из микросервиса: {}, {}", token, friendsIds);

        List<FriendDto> friendDtoList = new ArrayList<>();
        for (UUID id : friendsIds) {
            AccountMeDTO account = accountClient.getAccount(token, id);
            friendDtoList.add(FriendDto.builder()
                            .firstName(account.getFirstName())
                            .lastName(account.getLastName())
                            .photoUrl(account.getPhoto())
                    .build());
        }
        return friendDtoList;
    }
}

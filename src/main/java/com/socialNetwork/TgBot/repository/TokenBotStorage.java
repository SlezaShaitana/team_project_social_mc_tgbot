package com.socialNetwork.TgBot.repository;


import com.socialNetwork.TgBot.dto.TokenBot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenBotStorage  extends CrudRepository<TokenBot,String> {

}

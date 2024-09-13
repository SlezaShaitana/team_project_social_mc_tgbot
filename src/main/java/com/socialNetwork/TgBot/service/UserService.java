package com.socialNetwork.TgBot.service;


import com.socialNetwork.TgBot.entity.User;
import com.socialNetwork.TgBot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getByEmail(String username) {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    public UserDetailsService userDetailsService() {
        return this::getByEmail;
    }

}


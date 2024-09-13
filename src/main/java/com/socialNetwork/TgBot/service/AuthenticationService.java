package com.socialNetwork.TgBot.service;


import com.socialNetwork.TgBot.client.AuthClient;
import com.socialNetwork.TgBot.dto.*;
import com.socialNetwork.TgBot.entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService  {


    private final AuthClient authClient;
    private final JwtService jwtService;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;


//    public AuthenticateResponseDto login(AuthenticateDto authenticateDto, HttpServletResponse response) {
//
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateDto.getEmail(), authenticateDto.getPassword()));
//        User user = userService.getByEmail(authenticateDto.getEmail());
//        var jwt = jwtService.generateToken(user);
//        var refreshToken = refreshTokenService.generateRefreshTokenByUserId(user.getId());
//        Cookie cookie = new Cookie("Refresh_token", refreshToken.getToken());
//        Cookie cookie2 = new Cookie("Access_token", jwt);
//        response.addCookie(cookie);
//        response.addCookie(cookie2);
//        response.addHeader("Authorization", "Bearer " + jwt);
//        return new AuthenticateResponseDto(jwt, refreshToken.getToken());
//    }

    // пример
    private AuthenticateResponseDto get(){
        AuthenticateDto authenticateDto = new AuthenticateDto();
        return authClient.login(authenticateDto);
    }

}
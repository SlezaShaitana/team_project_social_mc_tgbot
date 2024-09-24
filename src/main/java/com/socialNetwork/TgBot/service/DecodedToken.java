package com.socialNetwork.TgBot.service;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Getter
@Slf4j
public class DecodedToken {

    private String id;
    private String email;
    private List<String> role;

    public static DecodedToken getDecoded(String encodedToken) throws UnsupportedEncodingException {
        String[] pieces = encodedToken.split("\\.");
        String b64payload = pieces[1];

        String jsonString = new String(Base64.decodeBase64(b64payload), "UTF-8");
        log.info("Payload token: {}", jsonString);

        return new Gson().fromJson(jsonString, DecodedToken.class);
    }
}


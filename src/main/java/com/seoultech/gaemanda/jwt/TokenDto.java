package com.seoultech.gaemanda.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenDto {

    private String accessToken;

    private String refreshToken;

    public static TokenDto from(String accessToken, String refreshToken) {
        return new TokenDto(accessToken, refreshToken);
    }

}

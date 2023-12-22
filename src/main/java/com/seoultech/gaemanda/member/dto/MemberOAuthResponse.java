package com.seoultech.gaemanda.member.dto;

import com.seoultech.gaemanda.jwt.TokenDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberOAuthResponse {

  private String accessToken;
  private String refreshToken;
  private Long memberId;

  public static MemberOAuthResponse from(TokenDto token, Long memberId) {
    return new MemberOAuthResponse(token.getAccessToken(), token.getRefreshToken(), memberId);
  }

}

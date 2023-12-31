package com.seoultech.gaemanda.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenProvider {

  private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24;
  private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 30;

  private final Key key;

  public TokenProvider() {
    byte[] decode = Decoders.BASE64.decode(JwtConfig.JWT_SECRET);
    this.key = Keys.hmacShaKeyFor(decode);
  }

  public TokenDto generateToken(Long id) {

    long now = (new Date()).getTime();

    Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
    String accessToken = Jwts.builder()
        .setId(String.valueOf(id))
        .setExpiration(accessTokenExpiresIn)
        .signWith(key, SignatureAlgorithm.HS512)
        .compact();

    Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
    String refreshToken = Jwts.builder()
        .setId(String.valueOf(id))
        .setExpiration(refreshTokenExpiresIn)
        .signWith(key, SignatureAlgorithm.HS512)
        .compact();

    return TokenDto.from(accessToken, refreshToken);
  }

  public TokenDto refreshAccessToken(String id) {
    long now = (new Date()).getTime();

    Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
    String accessToken = Jwts.builder()
        .setId(id)
        .setExpiration(accessTokenExpiresIn)
        .signWith(key, SignatureAlgorithm.HS512)
        .compact();

    return TokenDto.from(accessToken, "");
  }

}

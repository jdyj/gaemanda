package com.seoultech.gaemanda.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class JwtFilter implements Filter {

  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String BEARER_PREFIX = "Bearer ";
  private static final String[] whitelist = {
      "/images",
      "/docs/api-doc.html", "/favicon.ico",
      "/swagger-resources/**", "/v3/api-docs", "/swagger*/**", "/webjars/**", "/swagger-resources"
  };

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    try {
      HttpServletRequest httpRequest = (HttpServletRequest) request;

      String jwt = resolveToken(httpRequest);
      String requestURI = httpRequest.getRequestURI();
      // 2. validateToken 으로 토큰 유효성 검사
      validateToken(jwt, httpRequest);
      chain.doFilter(request, response);
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
      HttpServletResponse res = (HttpServletResponse) response;
      res.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    } catch (Exception e) {
      HttpServletResponse res = (HttpServletResponse) response;
      res.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
  }

  private boolean isCheckPath(String requestURI) {
    return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
  }

  // Request Header 에서 토큰 정보를 꺼내오기
  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }


  private void validateToken(String token, HttpServletRequest servletRequest) {

    byte[] decode = Decoders.BASE64.decode(JwtConfig.JWT_SECRET);
    Key key = Keys.hmacShaKeyFor(decode);

    try {
      if (token != null) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
        servletRequest.setAttribute("memberId", claims.get("jti", String.class));
      }
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      log.info("잘못된 JWT 서명입니다.");
    } catch (ExpiredJwtException e) {
      log.info("만료된 JWT 토큰입니다.");
    } catch (UnsupportedJwtException e) {
      log.info("지원되지 않는 JWT 토큰입니다.");
    } catch (IllegalArgumentException e) {
      log.info("JWT 토큰이 잘못되었습니다.");
    }
  }

}
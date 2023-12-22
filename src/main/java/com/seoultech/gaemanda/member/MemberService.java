package com.seoultech.gaemanda.member;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.seoultech.gaemanda.exception.ExistNicknameException;
import com.seoultech.gaemanda.image.Image;
import com.seoultech.gaemanda.image.ImageService;
import com.seoultech.gaemanda.jwt.TokenDto;
import com.seoultech.gaemanda.jwt.TokenProvider;
import com.seoultech.gaemanda.member.dto.*;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final ImageService imageService;
  private final RestTemplate restTemplate;
  private final TokenProvider tokenProvider;

  public MemberOAuthResponse kakaoLogin(String accessToken) {
    String apiUrl = "https://kapi.kakao.com/v2/user/me";
    String responseBody = get(apiUrl, accessToken);

    JsonParser parser = new JsonParser();
    JsonElement element = parser.parse(responseBody);

    JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
    JsonObject profile = kakaoAccount.getAsJsonObject().get("profile").getAsJsonObject();

    String name = profile.getAsJsonObject().get("nickname").getAsString();
    String email = kakaoAccount.getAsJsonObject().get("email").getAsString();

    Optional<Member> memberOptional = memberRepository.findMemberByEmail(email);

    Member member;
    if (memberOptional.isPresent()) {
      member = memberOptional.get();
    } else {
//      Image profileImage = imageService.findDefaultProfileImage();
      member = memberRepository.save(new Member(email));
    }

    TokenDto token = tokenProvider.generateToken(member.getId());
    return MemberOAuthResponse.from(token, member.getId());
  }

  public MemberProfileResponse getProfile(Long memberId) {
    Member member = findByMemberId(memberId);
    return MemberProfileResponse.from(member);
  }

  public Long makeProfile(MemberProfileDto.Request request) {
    Image image = imageService.storeFile(request.getProfileImage());
    Member member = new Member(request.getJibunAddress(), request.getLatitude(), request.getLongitude(), request.getNickname(), request.getBirthday(),
        request.getGender(), image);

    Member savedMember = memberRepository.save(member);

    return savedMember.getId();
  }

  public void editProfile(Long memberId, EditMemberProfileRequest request) {
    Member member = findByMemberId(memberId);
    member.setNickname(request.getNickname());
    member.setBirthday(request.getBirthday());
    member.setGender(request.getGender());

    // 기본이미지
    if (request.getProfileImage() == null && request.getIsFileChange()) {
      member.setProfileImage(imageService.findDefaultFolderImage());
    }

    // 변경이미지
    if (request.getProfileImage() != null && request.getIsFileChange()) {
      Image image = imageService.storeFile(request.getProfileImage());
      member.setProfileImage(image);
    }

  }

  public void checkNickname(String nickname) {
    if (memberRepository.existsMemberByNickname(nickname)) {
      return;
    } else {
      throw new ExistNicknameException();
    }
  }

  public Member findByMemberId(Long memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow();
  }

  public void setDeviceToken(Long memberId, String deviceToken) {
    Member member = findByMemberId(memberId);
    member.setDeviceToken(deviceToken);
    memberRepository.saveAndFlush(member);
  }

  private String get(String apiUrl, String accessToken) {

    try {
      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", "Bearer " + accessToken);

      HttpEntity entity = new HttpEntity(headers);
      ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity,
          String.class);

      return response.getBody();
    } catch (HttpStatusCodeException e) {
      throw new RuntimeException("API 요청과 응답 실패", e);
    }
  }
}

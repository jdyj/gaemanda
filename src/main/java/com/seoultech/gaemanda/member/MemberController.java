package com.seoultech.gaemanda.member;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import com.seoultech.gaemanda.login.LoginUser;
import com.seoultech.gaemanda.member.dto.EditMemberProfileRequest;
import com.seoultech.gaemanda.member.dto.MemberOAuthRequest;
import com.seoultech.gaemanda.member.dto.MemberOAuthResponse;
import com.seoultech.gaemanda.member.dto.MemberProfileDto;
import com.seoultech.gaemanda.member.dto.MemberProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

  private final MemberService memberService;

  @Operation(summary = "카카오 로그인")
  @PostMapping("/kakao")
  public ResponseEntity<MemberOAuthResponse> kakaoLogin(@RequestBody MemberOAuthRequest request) {
    return ResponseEntity.ok().body(memberService.kakaoLogin(request.getAccessToken()));
  }

  @Operation(summary = "멤버 프로필 조회")
  @GetMapping("/profile")
  public ResponseEntity<MemberProfileResponse> getProfile(@LoginUser Long memberId) {
    return ResponseEntity.ok().body(memberService.getProfile(memberId));
  }

  @Operation(summary = "멤버 프로필 생성")
  @PostMapping(value = "/profile", consumes = MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Long> makeProfile(@ModelAttribute MemberProfileDto.Request request) {
    return ResponseEntity.ok().body(memberService.makeProfile(request));
  }

  @Operation(summary = "멤버 프로필 수정")
  @PatchMapping(value = "/profile", consumes = MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> editProfile(@LoginUser Long memberId,
      @ModelAttribute EditMemberProfileRequest request) {
    memberService.editProfile(memberId, request);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Operation(summary = "닉네임 중복 확인")
  @GetMapping("/duplication/{nickname}")
  public ResponseEntity<Void> duplicateNickname(@PathVariable String nickname) {
    memberService.checkNickname(nickname);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}

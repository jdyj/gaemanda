package com.seoultech.gaemanda.member;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import com.seoultech.gaemanda.dto.SingleResponse;
import com.seoultech.gaemanda.login.LoginUser;
import com.seoultech.gaemanda.member.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
  public ResponseEntity<SingleResponse<MemberOAuthResponse>> kakaoLogin(@RequestBody MemberOAuthRequest request) {
    return ResponseEntity.ok().body(SingleResponse.from(memberService.kakaoLogin(request.getAccessToken())));
  }

  @Operation(summary = "멤버 프로필 조회")
  @GetMapping("/profile")
  public ResponseEntity<SingleResponse<MemberProfileResponse>> getProfile(@Parameter(hidden = true) @LoginUser Long memberId) {
    return ResponseEntity.ok().body(SingleResponse.from(memberService.getProfile(memberId)));
  }

  @Operation(summary = "멤버 프로필 생성")
  @PostMapping(value = "/profile", consumes = MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<SingleResponse<Long>> makeProfile(@ModelAttribute MemberProfileDto.Request request) {
    return ResponseEntity.ok().body(SingleResponse.from(memberService.makeProfile(request)));
  }

  @Operation(summary = "멤버 프로필 수정")
  @PatchMapping(value = "/profile", consumes = MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> editProfile(@Parameter(hidden = true) @LoginUser Long memberId,
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

  @Operation(summary = "")
  @PostMapping("/deviceToken")
  public ResponseEntity<Void> deviceToken(@RequestBody MemberDeviceTokenRequest request,
                  @Parameter(hidden = true) @LoginUser Long memberId) {
    memberService.setDeviceToken(memberId, request.getDeviceToken());
    return new ResponseEntity<>(HttpStatus.OK);
  }

}

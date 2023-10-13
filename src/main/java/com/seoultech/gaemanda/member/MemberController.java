package com.seoultech.gaemanda.member;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import com.seoultech.gaemanda.login.LoginUser;
import com.seoultech.gaemanda.member.dto.EditMemberProfileRequest;
import com.seoultech.gaemanda.member.dto.MemberProfileDto;
import com.seoultech.gaemanda.member.dto.MemberProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
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

  @Operation(summary = "멤버 프로필 조회")
  @GetMapping("/profile")
  public MemberProfileResponse getProfile(@LoginUser Long memberId) {
    return memberService.getProfile(memberId);
  }

  @Operation(summary = "멤버 프로필 생성")
  @PostMapping(value = "/profile", consumes = MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Long> makeProfile(@ModelAttribute MemberProfileDto.Request request) {
    return ResponseEntity.ok().body(memberService.makeProfile(request));
  }

  @Operation(summary = "멤버 프로필 수정")
  @PatchMapping(value = "/profile", consumes = MULTIPART_FORM_DATA_VALUE)
  public void editProfile(@LoginUser Long memberId,
      @ModelAttribute EditMemberProfileRequest request) {
    memberService.editProfile(memberId, request);
  }

}

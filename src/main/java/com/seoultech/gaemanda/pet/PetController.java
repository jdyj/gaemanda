package com.seoultech.gaemanda.pet;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import com.seoultech.gaemanda.dto.ListResponse;
import com.seoultech.gaemanda.login.LoginUser;
import com.seoultech.gaemanda.member.Member;
import com.seoultech.gaemanda.member.MemberService;
import com.seoultech.gaemanda.pet.dto.EditPetProfileRequest;
import com.seoultech.gaemanda.pet.dto.PetProfileRequest;
import com.seoultech.gaemanda.pet.dto.PetProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pet")
public class PetController {

  private final MemberService memberService;
  private final PetService petService;

  @Operation(summary = "펫 프로필 조회")
  @GetMapping("/profile")
  public ResponseEntity<ListResponse<PetProfileResponse>> getProfile(@LoginUser Long memberId) {
    Member member = memberService.findByMemberId(memberId);
    return ResponseEntity.ok()
        .body(ListResponse.from(petService.getProfile(member)));
  }

  @Operation(summary = "펫 프로필 생성")
  @PostMapping("/profile")
  public ResponseEntity<Long> getProfile(@LoginUser Long memberId, PetProfileRequest request) {
    Member member = memberService.findByMemberId(memberId);
    return ResponseEntity.ok()
        .body(petService.makeProfile(request, member));
  }

  @Operation(summary = "펫 프로필 수정")
  @PatchMapping(value = "/profile", consumes = MULTIPART_FORM_DATA_VALUE)
  public void editProfile(@ModelAttribute EditPetProfileRequest request) {
    petService.editProfile(request);
  }
}

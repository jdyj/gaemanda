package com.seoultech.gaemanda.pet;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import com.seoultech.gaemanda.dto.ListResponse;
import com.seoultech.gaemanda.dto.SingleResponse;
import com.seoultech.gaemanda.login.LoginUser;
import com.seoultech.gaemanda.member.Member;
import com.seoultech.gaemanda.member.MemberService;
import com.seoultech.gaemanda.pet.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pet")
public class PetController {

  private final MemberService memberService;
  private final PetService petService;

  @Operation(summary = "펫 프로필 조회")
  @GetMapping("/profile")
  public ResponseEntity<ListResponse<PetProfileResponse>> getProfile(
      @Parameter(hidden = true) @LoginUser Long memberId) {
    Member member = memberService.findByMemberId(memberId);
    return ResponseEntity.ok()
        .body(ListResponse.from(petService.getListProfile(member)));
  }

  @Operation(summary = "펫 프로필 생성")
  @PostMapping("/profile")
  public ResponseEntity<SingleResponse<Long>> getProfile(@Parameter(hidden = true) @LoginUser Long memberId, PetProfileRequest request) {
    Member member = memberService.findByMemberId(memberId);
    return ResponseEntity.ok()
        .body(SingleResponse.from(petService.makeProfile(request, member)));
  }

  @Operation(summary = "펫 프로필 수정")
  @PatchMapping(value = "/profile", consumes = MULTIPART_FORM_DATA_VALUE)
  public void editProfile(@ModelAttribute EditPetProfileRequest request) {
    petService.editProfile(request);
  }

  @Operation(summary = "첫번째 펫 조회")
  @GetMapping("/profile/first")
  public ResponseEntity<SingleResponse<PetProfileResponse>> getFirstProfile(
          @Parameter(hidden = true) @LoginUser Long memberId) {
    Member member = memberService.findByMemberId(memberId);
    return ResponseEntity.ok()
        .body(SingleResponse.from(petService.getFirstProfile(member)));
  }

  @Operation(summary = "펫 종류 조회")
  @GetMapping("/species")
  public ResponseEntity<ListResponse<PetSpeciesResponse>> getSpecies() {
    List<PetSpeciesResponse> collect = Arrays.stream(Species.values())
        .map(species -> PetSpeciesResponse.from(species.name()))
        .collect(Collectors.toList());
    return ResponseEntity.ok()
        .body(ListResponse.from(collect));
  }

  @Operation(summary = "상대방 펫 조회 (map 용)")
  @GetMapping("/{memberId}")
  public ResponseEntity<PetProfileByMap> getPetProfileForMap(@PathVariable Long memberId) {
    Member member = memberService.findByMemberId(memberId);
    return ResponseEntity.ok()
            .body(petService.getPetProfileForMap(member));
  }

}

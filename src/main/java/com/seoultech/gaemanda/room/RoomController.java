package com.seoultech.gaemanda.room;

import com.seoultech.gaemanda.dto.ListResponse;
import com.seoultech.gaemanda.login.LoginUser;
import com.seoultech.gaemanda.member.Member;
import com.seoultech.gaemanda.member.MemberService;
import com.seoultech.gaemanda.room.dto.MakeRoomRequest;
import com.seoultech.gaemanda.room.dto.RoomInfoResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

  private final RoomService roomService;
  private final MemberService memberService;

  @PostMapping
  public ResponseEntity<Long> makeRoom(@RequestBody MakeRoomRequest request) {
    Member sender = memberService.findByMemberId(request.getSender());
    Member member = memberService.findByMemberId(request.getSender());
    return ResponseEntity.ok()
            .body(roomService.save(sender, member));
  }

  @GetMapping
  public ResponseEntity<ListResponse<RoomInfoResponse>> getRoomInfo(@Parameter(hidden = true) @LoginUser Long memberId) {
    Member member = memberService.findByMemberId(memberId);
    roomService.getRoomInfo(member);
    return null;
  }



}

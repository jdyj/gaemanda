package com.seoultech.gaemanda.room;

import com.seoultech.gaemanda.member.Member;
import com.seoultech.gaemanda.member.MemberService;
import com.seoultech.gaemanda.room.dto.MakeRoomRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}

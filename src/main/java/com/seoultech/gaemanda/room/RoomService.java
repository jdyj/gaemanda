package com.seoultech.gaemanda.room;

import com.seoultech.gaemanda.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

  private final RoomRepository roomRepository;

  public Long save(Member sender, Member member) {
    Room savedRoom = roomRepository.save(new Room(sender, member));
    return savedRoom.getId();
  }

  public Room findById(Long roomId) {
    return roomRepository.findById(roomId)
        .orElseThrow();
  }

}

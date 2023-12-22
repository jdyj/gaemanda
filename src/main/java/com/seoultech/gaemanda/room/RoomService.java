package com.seoultech.gaemanda.room;

import com.seoultech.gaemanda.chat.ChatService;
import com.seoultech.gaemanda.member.Member;
import com.seoultech.gaemanda.room.dto.RoomInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

  private final RoomRepository roomRepository;
  private final ChatService chatService;

  public Long save(Member sender, Member member) {
    Room savedRoom = roomRepository.save(new Room(sender, member));
    return savedRoom.getId();
  }

  public List<RoomInfoResponse> getRoomInfo(Member member) {
    List<Room> roomList = roomRepository.findAllByMember1(member);
    List<Room> roomList2 = roomRepository.findAllByMember2(member);

    List<RoomInfoResponse> collect = new ArrayList<>(roomList.stream()
            .map(room -> RoomInfoResponse.from(room, room.getMember2(), chatService.chatMsg(room, member)))
            .toList());
    List<RoomInfoResponse> collect2 = roomList2.stream()
            .map(room -> RoomInfoResponse.from(room, room.getMember1(), chatService.chatMsg(room, member)))
            .toList();

    collect.addAll(collect2);

    return collect;
  }

  public Room findById(Long roomId) {
    return roomRepository.findById(roomId)
        .orElseThrow();
  }

  public Optional<Room> findByIdOptional(Long roomId) {
    return roomRepository.findById(roomId);
  }

}

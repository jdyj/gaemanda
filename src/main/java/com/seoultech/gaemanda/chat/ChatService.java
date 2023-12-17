package com.seoultech.gaemanda.chat;

import com.seoultech.gaemanda.member.Member;
import com.seoultech.gaemanda.member.MemberService;
import com.seoultech.gaemanda.room.Room;
import com.seoultech.gaemanda.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

  private final ChatRepository chatRepository;
  private final RoomService roomService;
  private final MemberService memberService;

  public Long save(Long roomId, Long senderId, String message) {

    Room room = roomService.findById(roomId);
    Member sender = memberService.findByMemberId(senderId);
    Chat chat = new Chat(message, room, sender);
    Chat savedChat = chatRepository.save(chat);
    return savedChat.getId();
  }

}

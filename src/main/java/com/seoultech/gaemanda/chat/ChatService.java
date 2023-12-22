package com.seoultech.gaemanda.chat;

import com.seoultech.gaemanda.alarm.AlarmService;
import com.seoultech.gaemanda.config.fcm.FcmMessageService;
import com.seoultech.gaemanda.config.fcm.Note;
import com.seoultech.gaemanda.member.Member;
import com.seoultech.gaemanda.member.MemberService;
import com.seoultech.gaemanda.room.Room;
import com.seoultech.gaemanda.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

  private final ChatRepository chatRepository;
  private final MemberService memberService;
  private final FcmMessageService fcmMessageService;

  public Long save(Room room, Long senderId, String message) {

    Member sender = memberService.findByMemberId(senderId);
    Chat chat = new Chat(message, room, sender);
    Chat savedChat = chatRepository.save(chat);

    Note note = Note.makeNote(makeChatNote(sender, room.getMember2(), message));
    fcmMessageService.sendMessage(note);
    return savedChat.getId();
  }

  public Chat chatMsg(Room room, Member member) {
    return chatRepository.lastChat(room)
            .orElseGet(() -> new Chat("", room, member));
  }

  private Map<String, String> makeChatNote(Member sender, Member member, String message) {
    Map<String, String> data = new HashMap<>();
    data.put("subject", "Gaemanda");
    data.put("body", sender.getNickname());
    data.put("content", message);
    data.put("deviceToken", member.getDeviceToken());
    data.put("image", sender.getProfileImage().getStoreFileName());
    return data;
  }

}

package com.seoultech.gaemanda.websocket;

import com.seoultech.gaemanda.chat.ChatService;
import com.seoultech.gaemanda.room.Room;
import com.seoultech.gaemanda.room.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

  private final SimpMessageSendingOperations simpMessageSendingOperations;
  private final ChatService chatService;

  @MessageMapping("/chat")
  public void chatEnter(ChatMessage message) {
    log.info("chat enter memberId -> {}, channelId -> {}", message.getSender(),
        message.getRoomId());
    simpMessageSendingOperations.convertAndSend("/sub/room/" + message.getRoomId(),
        message);
    chatService.save(message.getRoomId(), message.getSender(), message.getMessage());
  }

  @MessageMapping("/map")
  public void mapEnter(MapMessage message) {
    log.info("map enter memberId -> {}", message.getSender());
    simpMessageSendingOperations.convertAndSend("/sub/map", message);
  }


}

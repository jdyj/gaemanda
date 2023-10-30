package com.seoultech.gaemanda.websocket;

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

  @MessageMapping("/chat")
  public void enter(Message message) {
    log.info("memberId -> {}", message.getSender());
    simpMessageSendingOperations.convertAndSend("/sub/room/" + message.getChannelId(),
        message);
  }


}

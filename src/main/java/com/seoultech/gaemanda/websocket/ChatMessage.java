package com.seoultech.gaemanda.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ChatMessage {

  private Long senderId;
  private Long roomId;
  private String message;

}

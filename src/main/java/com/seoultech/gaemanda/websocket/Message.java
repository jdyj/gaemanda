package com.seoultech.gaemanda.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Message {

  private String type;
  private String sender;
  private String channelId;
  private Object data;

}

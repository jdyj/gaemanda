package com.seoultech.gaemanda.websocket;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MapMessage {

  private Long senderId;
  private Long petId;
  private Double lng;
  private Double lat;

}

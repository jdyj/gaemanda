package com.seoultech.gaemanda.websocket;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MapMessage {

  private String type;
  private Long sender;
  private Double lng;
  private Double lat;
  private Object data;

}

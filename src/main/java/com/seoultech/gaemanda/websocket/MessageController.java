package com.seoultech.gaemanda.websocket;

import com.seoultech.gaemanda.chat.ChatService;
import com.seoultech.gaemanda.member.Member;
import com.seoultech.gaemanda.member.MemberService;
import com.seoultech.gaemanda.util.DistanceUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

  private final SimpMessageSendingOperations simpMessageSendingOperations;
  private final ChatService chatService;
  private final MemberService memberService;
  private final Map<Long, MapInfo> map = new HashMap<>();

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
    log.info("map enter memberId -> {}", message.getSenderId());
    map.put(message.getSenderId(), new MapInfo(message.getSenderId(), message.getPetId(), message.getLng(), message.getLng()));

    Member member = memberService.findByMemberId(message.getSenderId());

    List<MapInfo> collect = map.entrySet().stream()
            .filter((entry) -> !entry.getKey().equals(message.getSenderId()))
            .filter(entry -> DistanceUtil.distance(member.getLatitude(), member.getLongitude(), entry.getValue().getLat(), entry.getValue().getLng()) > 500.0)
            .map(Map.Entry::getValue)
            .collect(Collectors.toList());

    simpMessageSendingOperations.convertAndSend("/sub/map", collect);
  }

  @Data
  @AllArgsConstructor
  static class MapInfo {
    private Long memberId;
    private Long petId;
    private Double lng;
    private Double lat;
  }

}

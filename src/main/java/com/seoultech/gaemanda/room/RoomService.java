package com.seoultech.gaemanda.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

  private final RoomRepository roomRepository;

  public Room findById(Long roomId) {
    return roomRepository.findById(roomId)
        .orElseThrow();
  }

}

package com.seoultech.gaemanda.map;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MapController {

  private final SimpUserRegistry simpUserRegistry;

  public void getUserInMap() {
    simpUserRegistry.getUserCount();
  }

}

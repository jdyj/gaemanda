package com.seoultech.gaemanda.map;

import com.seoultech.gaemanda.websocket.MapMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapService {

  private final Map<Long, Location> locationMap = new HashMap<>();

  public void saveLocation(MapMessage mapMessage) {
    locationMap.put(mapMessage.getSender(),
        new Location(mapMessage.getSender(), mapMessage.getLat(), mapMessage.getLng()));
  }

  public void removeLocation(MapMessage mapMessage) {
    locationMap.remove(mapMessage.getSender());
  }

  public List<Location> getUserLocation() {
    return new ArrayList<>(locationMap.values());
  }

}

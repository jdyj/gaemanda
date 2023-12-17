package com.seoultech.gaemanda.websocket;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class MessageControllerTests {

    private Map<Long, MapInfo> map = new HashMap<>();

    @Test
    void test() {

        map.put(1L, new MapInfo("type2", 1L, 10.0, 10.0, null));
        map.put(1L, new MapInfo("type", 1L, 10.0, 10.0, null));

        System.out.println(map.get(1L).type);

    }


    static class MapInfo {
        private String type;
        private Long sender;
        private Double lng;
        private Double lat;
        private Object data;

        public MapInfo(String type, Long sender, Double lng, Double lat, Object data) {
            this.type = type;
            this.sender = sender;
            this.lng = lng;
            this.lat = lat;
            this.data = data;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Long getSender() {
            return sender;
        }

        public void setSender(Long sender) {
            this.sender = sender;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }


}
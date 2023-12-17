package com.seoultech.gaemanda.util;

public class DistanceUtil {

    public static Double distance(Double lat1, Double lng1, Double lat2, Double lng2) {
        Long R = 6371000L; // 지구 반지름 (단위: m)
        Double dLat = deg2rad(lat2 - lat1);
        Double dLon = deg2rad(lng2 - lng1);
        Double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                    Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                            Math.sin(dLon/2) * Math.sin(dLon/2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }
    private static Double deg2rad(Double deg) {
        return Math.abs(deg * (Math.PI/180));
    }

}

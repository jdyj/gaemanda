package com.seoultech.gaemanda.config.fcm;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Note {

    private String subject;
    private String content;
    private Map<String, String> data;
    private String image;
    private String deviceToken;

    public static Note makeNote(Map<String, String> collect) {

        Map<String, String> tempData = new HashMap<>(collect);
        return new Note(
                "DAYO",
                tempData.get("content"),
                tempData,
                tempData.get("image"),
                tempData.get("deviceToken")
        );
    }


}
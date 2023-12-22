package com.seoultech.gaemanda.room.dto;

import com.seoultech.gaemanda.chat.Chat;
import com.seoultech.gaemanda.member.Member;
import com.seoultech.gaemanda.room.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class RoomInfoResponse {

    private Long roomId;
    private String nickname;
    private String chatMsg;
    private String profileImage;
    private String chatDate;

    public static RoomInfoResponse from(Room room, Member member, Chat chat) {
        return new RoomInfoResponse(room.getId(), member.getNickname(), chat.getMessage(), member.getProfileImage().getStoreFileName(), chat.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
    }

}

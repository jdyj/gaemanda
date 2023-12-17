package com.seoultech.gaemanda.room.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MakeRoomRequest {

    private Long sender;
    private Long member;

}

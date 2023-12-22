package com.seoultech.gaemanda.room;

import com.seoultech.gaemanda.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAllByMember1(Member member);
    List<Room> findAllByMember2(Member member);

}

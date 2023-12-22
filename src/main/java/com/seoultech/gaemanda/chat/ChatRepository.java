package com.seoultech.gaemanda.chat;


import com.seoultech.gaemanda.member.Member;
import com.seoultech.gaemanda.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {


    @Query("select c from Chat c order by c.createdDate desc limit 1")
    Optional<Chat> lastChat(Room room);

}

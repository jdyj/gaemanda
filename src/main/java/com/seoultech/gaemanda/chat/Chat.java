package com.seoultech.gaemanda.chat;

import com.seoultech.gaemanda.member.Member;
import com.seoultech.gaemanda.room.Room;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
public class Chat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String message;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Room room;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sender_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Member sender;

  public Chat(String message, Room room, Member sender) {
    this.message = message;
    this.room = room;
    this.sender = sender;
  }

  public Chat() {
  }
}

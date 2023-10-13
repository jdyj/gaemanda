package com.seoultech.gaemanda.room;

import com.seoultech.gaemanda.member.Member;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToOne
  @JoinColumn(name = "member_id1", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Member member1;

  @ManyToOne
  @JoinColumn(name = "member_id2", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Member member2;

}

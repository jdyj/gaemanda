package com.seoultech.gaemanda.pet;

import com.seoultech.gaemanda.image.Image;
import com.seoultech.gaemanda.member.Member;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Pet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Member member;

  @Setter
  private String name;

  @Setter
  private String birthday;

  @Setter
  @Enumerated(EnumType.STRING)
  private Species species;

  private String gender;

  @Setter
  private Integer weight;

  @Setter
  private Boolean isNeutered;

  @Setter
  private Integer personality;

  @Setter
  @OneToOne
  @JoinColumn(name = "image_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Image profileImage;

  public Pet(Member member, String name, String birthday, Species species, String gender,
      Integer weight, Boolean isNeutered, Integer personality,
      Image profileImage) {
    this.member = member;
    this.name = name;
    this.birthday = birthday;
    this.species = species;
    this.gender = gender;
    this.weight = weight;
    this.isNeutered = isNeutered;
    this.personality = personality;
    this.profileImage = profileImage;
  }
}

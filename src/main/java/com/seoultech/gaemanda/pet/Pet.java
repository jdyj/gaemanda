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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class Pet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @Getter
  private Member member;

  @Setter
  @Getter
  private String name;

  @Setter
  @Getter
  private String birthday;

  @Setter
  @Getter
  @Enumerated(EnumType.STRING)
  private Species species;

  @Getter
  private String gender;

  @Setter
  @Getter
  private Integer weight;

  @Setter
  @Getter
  private Boolean isNeutered;

  private String personalities;

  @Setter
  @Getter
  @OneToOne
  @JoinColumn(name = "image_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Image profileImage;

  public Pet(Member member, String name, String birthday, Species species, String gender,
      Integer weight, Boolean isNeutered, List<Integer> personalityList,
      Image profileImage) {

    String personality = personalityList
        .stream()
        .map(String::valueOf)
        .collect(Collectors.joining(","));

    this.member = member;
    this.name = name;
    this.birthday = birthday;
    this.species = species;
    this.gender = gender;
    this.weight = weight;
    this.isNeutered = isNeutered;
    this.personalities = personality;
    this.profileImage = profileImage;
  }

  public List<Integer> getPersonalities() {
    return Arrays.stream(personalities.split(","))
        .map(Integer::valueOf)
        .collect(Collectors.toList());
  }

  public void setPersonalities(List<Integer> personalityList) {
    this.personalities = personalityList
        .stream()
        .map(String::valueOf)
        .collect(Collectors.joining(","));
  }
}

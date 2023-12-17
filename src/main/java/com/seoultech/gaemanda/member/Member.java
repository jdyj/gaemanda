package com.seoultech.gaemanda.member;

import com.seoultech.gaemanda.image.Image;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;

  private String address;

  @Setter
  private String nickname;

  @Setter
  private String birthday;

  @Setter
  private String gender;

  @Setter
  @OneToOne
  @JoinColumn(name = "image_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Image profileImage;

  @Setter
  private String deviceToken;

  public Member(String email) {
    this.email = email;
  }

  public Member(String address, String nickname, String birthday,
      String gender, Image profileImage) {
    this.address = address;
    this.nickname = nickname;
    this.birthday = birthday;
    this.gender = gender;
    this.profileImage = profileImage;
  }
}

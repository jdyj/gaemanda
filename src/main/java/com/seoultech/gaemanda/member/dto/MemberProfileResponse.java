package com.seoultech.gaemanda.member.dto;

import com.seoultech.gaemanda.member.Member;
import com.seoultech.gaemanda.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberProfileResponse {

  private Long memberId;
  private String nickname;
  private String profileImage;
  private String gender;
  private Integer age;
  private String birthday;

  public static MemberProfileResponse from(Member member) {
    return new MemberProfileResponse(member.getId(), member.getNickname(),
        member.getProfileImage().getStoreFileName(), member.getGender(),
        DateUtil.getFullAge(member.getBirthday()), member.getBirthday());
  }

}

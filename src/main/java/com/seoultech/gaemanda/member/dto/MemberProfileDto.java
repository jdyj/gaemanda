package com.seoultech.gaemanda.member.dto;

import com.seoultech.gaemanda.member.Member;
import com.seoultech.gaemanda.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class MemberProfileDto {

  @Data
  @AllArgsConstructor
  static public class Request {

    private String nickname;
    private MultipartFile profileImage;
    private String gender;
    private Integer age;
    private String birthday;
    private String address;
  }

  @Data
  @AllArgsConstructor
  static public class Response {

    private Long memberId;
    private String nickname;
    private String profileImage;
    private String gender;
    private Integer age;
    private String birthday;

    public static MemberProfileDto.Response from(Member member) {
      return new MemberProfileDto.Response(member.getId(), member.getNickname(),
          member.getProfileImage().getStoreFileName(), member.getGender(),
          DateUtil.getFullAge(member.getBirthday()), member.getBirthday());
    }
  }

}

package com.seoultech.gaemanda.member.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class EditMemberProfileRequest {

  private String nickname;
  private String birthday;
  private String gender;
  private MultipartFile profileImage;
  private Boolean isFileChange;


}

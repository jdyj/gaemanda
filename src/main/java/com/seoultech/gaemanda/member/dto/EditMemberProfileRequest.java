package com.seoultech.gaemanda.member.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditMemberProfileRequest {

  private String nickname;
  private String birthday;
  private String gender;
  private MultipartFile profileImage;
  private Boolean isFileChange;


}

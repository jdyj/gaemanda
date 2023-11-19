package com.seoultech.gaemanda.pet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class EditPetProfileRequest {

  private Long petId;
  private String name;
  private String birthday;
  private String species;
  private Integer weight;
  private Boolean isNeutered;
  private Integer personality;
  private MultipartFile profileImage;
  private Boolean isFileChange;

}

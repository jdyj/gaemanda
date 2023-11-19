package com.seoultech.gaemanda.pet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class PetProfileRequest {

  private String name;
  private String birthday;
  private String species;
  private Integer weight;
  private Boolean isNeutered;
  private Integer personality;
  private MultipartFile profileImage;
  private Boolean isFileChange;

}

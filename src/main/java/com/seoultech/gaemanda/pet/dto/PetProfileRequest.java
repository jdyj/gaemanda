package com.seoultech.gaemanda.pet.dto;

import java.util.List;
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
  private List<Integer> personalities;
  private MultipartFile profileImage;
  private Boolean isFileChange;
  private String gender;

}

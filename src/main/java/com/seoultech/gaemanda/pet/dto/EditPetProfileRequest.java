package com.seoultech.gaemanda.pet.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditPetProfileRequest {

  private Long petId;
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

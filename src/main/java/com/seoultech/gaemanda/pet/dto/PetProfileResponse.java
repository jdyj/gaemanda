package com.seoultech.gaemanda.pet.dto;

import com.seoultech.gaemanda.pet.Pet;
import com.seoultech.gaemanda.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class PetProfileResponse {

  private Long petId;
  private String name;
  private Integer age;
  private String birthday;
  private String species;
  private Integer weight;
  private Boolean isNeutered;
  private String personality;
  private String profileImage;

  public static PetProfileResponse from(Pet pet) {
    return new PetProfileResponse(pet.getId(), pet.getName(),
        DateUtil.getFullAge(pet.getBirthday()), pet.getBirthday(),
        pet.getSpecies(), pet.getWeight(), pet.getIsNeutered(), pet.getPersonality(),
        pet.getProfileImage().getStoreFileName());
  }

}

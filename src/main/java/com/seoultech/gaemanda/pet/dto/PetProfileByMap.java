package com.seoultech.gaemanda.pet.dto;

import com.seoultech.gaemanda.pet.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PetProfileByMap {

    private Long memberId;
    private String profileImage;
    private String name;
    private String gender;
    private String birthday;
    private List<Integer> personalities;

    public static PetProfileByMap from(Pet pet) {
        return new PetProfileByMap(pet.getMember().getId(),
                pet.getProfileImage().getStoreFileName(),
                pet.getName(),
                pet.getGender(),
                pet.getBirthday(),
                pet.getPersonalities());
    }

}

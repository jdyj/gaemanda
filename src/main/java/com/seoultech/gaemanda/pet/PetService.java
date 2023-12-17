package com.seoultech.gaemanda.pet;

import com.seoultech.gaemanda.image.Image;
import com.seoultech.gaemanda.image.ImageService;
import com.seoultech.gaemanda.member.Member;
import com.seoultech.gaemanda.pet.dto.EditPetProfileRequest;
import com.seoultech.gaemanda.pet.dto.PetProfileByMap;
import com.seoultech.gaemanda.pet.dto.PetProfileRequest;
import com.seoultech.gaemanda.pet.dto.PetProfileResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PetService {

  private final PetRepository petRepository;
  private final ImageService imageService;

  public List<PetProfileResponse> getListProfile(Member member) {
    List<Pet> petList = findByMember(member);
    return petList.stream()
        .map(PetProfileResponse::from)
        .collect(Collectors.toList());
  }

  public PetProfileResponse getFirstProfile(Member member) {
    List<Pet> petList = findByMember(member);
    return petList.stream()
        .map(PetProfileResponse::from)
        .findFirst()
        .orElseThrow();
  }

  public Long makeProfile(PetProfileRequest request, Member member) {
    Image image = imageService.storeFile(request.getProfileImage());
    Pet pet = new Pet(member, request.getName(), request.getBirthday(),
        Species.valueOf(request.getSpecies()),
        request.getGender(),
        request.getWeight(), request.getIsNeutered(), request.getPersonalities(), image);

    Pet savedPet = petRepository.save(pet);

    return savedPet.getId();
  }

  public void editProfile(EditPetProfileRequest request) {

    Pet pet = findById(request.getPetId());

    pet.setBirthday(request.getBirthday());
    pet.setIsNeutered(request.getIsNeutered());
    pet.setPersonalities(request.getPersonalities());
    pet.setName(request.getName());
    pet.setSpecies(Species.valueOf(request.getSpecies()));
    pet.setWeight(request.getWeight());
    pet.setGender(request.getGender());

    // 기본이미지
    if (request.getProfileImage() == null && request.getIsFileChange()) {
      pet.setProfileImage(imageService.findDefaultFolderImage());
    }

    // 변경이미지
    if (request.getProfileImage() != null && request.getIsFileChange()) {
      Image image = imageService.storeFile(request.getProfileImage());
      pet.setProfileImage(image);
    }
    petRepository.flush();
  }

  public Pet findById(Long petId) {
    return petRepository.findById(petId)
        .orElseThrow();
  }

  public List<Pet> findByMember(Member member) {
    return petRepository.findByMember(member);
  }

  public PetProfileByMap getPetProfileForMap(Member member) {
    List<Pet> petByMember = petRepository.findByMember(member);
    return petByMember.stream()
            .findFirst()
            .map(PetProfileByMap::from)
            .orElseThrow();
  }

}

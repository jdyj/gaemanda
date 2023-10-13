package com.seoultech.gaemanda.pet;

import com.seoultech.gaemanda.image.Image;
import com.seoultech.gaemanda.image.ImageService;
import com.seoultech.gaemanda.member.Member;
import com.seoultech.gaemanda.member.MemberService;
import com.seoultech.gaemanda.pet.dto.EditPetProfileRequest;
import com.seoultech.gaemanda.pet.dto.PetProfileRequest;
import com.seoultech.gaemanda.pet.dto.PetProfileResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PetService {

  private final PetRepository petRepository;
  private final ImageService imageService;

  public List<PetProfileResponse> getProfile(Member member) {
    List<Pet> petList = findByMember(member);
    return petList.stream()
        .map(PetProfileResponse::from)
        .collect(Collectors.toList());
  }

  public Long makeProfile(PetProfileRequest request, Member member) {
    Image image = imageService.storeFile(request.getProfileImage());
    Pet pet = new Pet(member, request.getName(), request.getBirthday(), request.getSpecies(),
        "gender",
        request.getWeight(), request.getIsNeutered(), request.getPersonality(), image);

    Pet savedPet = petRepository.save(pet);

    return savedPet.getId();
  }

  public void editProfile(EditPetProfileRequest request) {

    Pet pet = findById(request.getPetId());

    pet.setBirthday(request.getBirthday());
    pet.setIsNeutered(request.getIsNeutered());
    pet.setPersonality(request.getPersonality());
    pet.setName(request.getName());
    pet.setSpecies(request.getSpecies());
    pet.setWeight(request.getWeight());

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

}

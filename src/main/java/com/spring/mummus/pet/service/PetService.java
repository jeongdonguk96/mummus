package com.spring.mummus.pet.service;

import com.spring.mummus.exception.enums.PetErrorCode;
import com.spring.mummus.exception.exception.PetException;
import com.spring.mummus.image.enums.ImageDomain;
import com.spring.mummus.image.service.ImageService;
import com.spring.mummus.image.service.S3Service;
import com.spring.mummus.pet.dto.RegisterPetRequest;
import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.spring.mummus.exception.enums.PetErrorCode.DUPLICATED_PET;

@Service
@RequiredArgsConstructor
public class PetService {

    private final S3Service s3Service;
    private final ImageService imageService;
    private final PetRepository petRepository;


    // 강아지를 등록한다.
    @Transactional
    public Pet createPet(RegisterPetRequest request, MultipartFile file, Long memberId) throws IOException {
        checkDuplicatedPet(request, memberId);
        Pet pet = petRepository.save(request.toEntity(memberId));
        s3Service.upload(file, ImageDomain.PET, memberId);
        String imageUrl = imageService.createImage(file, ImageDomain.PET, pet.getId(), memberId);
        updateProfileImage(pet, imageUrl);

        return pet;
    }


    // 사용자의 강아지를 조회한다.
    @Transactional
    public List<Pet> getPets(Long memberId) {
        List<Pet> myPets = petRepository.findMyPets(memberId);

        if (myPets.isEmpty()) {
            throw new PetException(PetErrorCode.MEMBER_HAS_NO_PETS);
        }

        List<String> imageUrls = s3Service.getFiles(myPets);

        return myPets;
    }


    // 강아지 존재 여부를 확인한다.
    @Transactional(readOnly = true)
    public Pet findById(Long id) {
        return petRepository.findById(id).orElseThrow(
                () -> new PetException(PetErrorCode.PET_NOT_FOUND));
    }


    // 강아지 등록 시 중복체크를 진행한다.
    private void checkDuplicatedPet(RegisterPetRequest request, Long memberId) {
        if (petRepository.existsByNameAndMemberId(request.getName(), memberId)) {
            throw new PetException(DUPLICATED_PET);
        }
    }


    // 강아지의 프로필 사진을 등록한다.
    private void updateProfileImage(Pet pet, String imageUrl) {
        pet.updateProfileImage(imageUrl);
    }

}

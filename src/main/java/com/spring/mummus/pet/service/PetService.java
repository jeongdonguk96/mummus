package com.spring.mummus.pet.service;

import com.spring.mummus.exception.enums.PetErrorCode;
import com.spring.mummus.exception.exception.PetException;
import com.spring.mummus.image.enums.ImageDomain;
import com.spring.mummus.image.service.ImageService;
import com.spring.mummus.image.service.S3Service;
import com.spring.mummus.pet.dto.CreatePetRequest;
import com.spring.mummus.pet.dto.GetMyPetsResponse;
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
    public Pet createPet(CreatePetRequest request, MultipartFile file, Long memberId) throws IOException {
        checkDuplicatedPet(request, memberId);
        Pet pet = petRepository.save(request.from(memberId));
        String filename = s3Service.upload(file, ImageDomain.PET, memberId);
        String imageUrl = imageService.createImage(filename, ImageDomain.PET, pet.getId(), memberId);
        pet.updateProfileImage(imageUrl);

        return pet;
    }


    // 사용자의 강아지를 조회한다.
    @Transactional
    public List<GetMyPetsResponse> getMyPets(Long memberId) {
        List<GetMyPetsResponse> myPets = petRepository.findMyPets(memberId);

        if (myPets.isEmpty()) {
            throw new PetException(PetErrorCode.MEMBER_HAS_NO_PETS);
        }

        return myPets;
    }


    // 강아지 존재 여부를 확인한다.
    @Transactional(readOnly = true)
    public Pet findById(Long id) {
        return petRepository.findById(id).orElseThrow(
                () -> new PetException(PetErrorCode.PET_NOT_FOUND));
    }


    // 강아지 등록 시 중복체크를 진행한다.
    private void checkDuplicatedPet(CreatePetRequest request, Long memberId) {
        if (petRepository.existsByNameAndMemberId(request.name(), memberId)) {
            throw new PetException(DUPLICATED_PET);
        }
    }

}

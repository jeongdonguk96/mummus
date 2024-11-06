package com.spring.mummus.pet.service;

import com.spring.mummus.exception.code.PetErrorCode;
import com.spring.mummus.exception.exception.CommonException;
import com.spring.mummus.pet.dto.DeletePetProfileImageRequest;
import com.spring.mummus.image.entity.Image;
import com.spring.mummus.image.enums.ImageDomain;
import com.spring.mummus.image.repository.ImageRepository;
import com.spring.mummus.image.service.ImageService;
import com.spring.mummus.image.service.S3Service;
import com.spring.mummus.pet.dto.CreatePetRequest;
import com.spring.mummus.pet.dto.GetMyPetsResponse;
import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.pet.enums.Gender;
import com.spring.mummus.pet.enums.PetType;
import com.spring.mummus.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.spring.mummus.exception.code.PetErrorCode.DUPLICATED_PET;

@Service
@RequiredArgsConstructor
public class PetService {

    @Value("https://${cloud.aws.s3.bucket}.s3.ap-northeast-2.amazonaws.com/")
    private String S3_URL;


    private final S3Service s3Service;
    private final ImageService imageService;
    private final PetRepository petRepository;
    private final ImageRepository imageRepository;


    // 강아지를 등록한다.
    @Transactional
    public Pet createPet(CreatePetRequest request, Long memberId) {
        checkDuplicatedPet(request, memberId);
        Pet pet = petRepository.save(request.from(memberId));
        imageService.modifyDomainId(pet);

        return pet;
    }


    // 사용자의 강아지를 조회한다.
    @Transactional(readOnly = true)
    public List<GetMyPetsResponse> getMyPets(Long memberId) {
        List<GetMyPetsResponse> myPets = petRepository.findMyPets(memberId);

        if (myPets.isEmpty()) {
            throw new CommonException(PetErrorCode.MEMBER_HAS_NO_PETS);
        }

        return myPets;
    }


    // 다른 강아지를 조회한다.
    @Transactional(readOnly = true)
    public Pet findById(Long id) {
        return petRepository.findById(id).orElseThrow(
                () -> new CommonException(PetErrorCode.PET_NOT_FOUND));
    }


    // 강아지의 이름을 변경한다.
    @Transactional
    public void modifyPetName(Long petId, String name) {
        petRepository.modifyPetName(petId, name);
    }


    // 강아지의 나이를 변경한다.
    @Transactional
    public void modifyPetAge(Long petId, int age) {
        petRepository.modifyPetAge(petId, age);
    }


    // 강아지의 생일을 변경한다.
    @Transactional
    public void modifyPetBirth(Long petId, String birth) {
        petRepository.modifyPetBirth(petId, birth);
    }


    // 강아지의 성별을 변경한다.
    @Transactional
    public void modifyPetGender(Long petId, Gender gender) {
        petRepository.modifyPetGender(petId, gender);
    }


    // 강아지의 견종을 변경한다.
    @Transactional
    public void modifyPetType(Long petId, PetType petType) {
        petRepository.modifyPetType(petId, petType);
    }


    // 강아지의 프로필 이미지를 등록한다.
    @Transactional
    public String createPetProfileImage(MultipartFile file, Long memberId) throws IOException {
        String fileName = s3Service.upload(file, ImageDomain.PET, memberId);
        Image image = Image.from(ImageDomain.PET, S3_URL + fileName, 1, memberId);
        imageRepository.save(image);

        return image.getPath();
    }


    // 강아지의 프로필 이미지를 변경한다.
    @Transactional
    public String modifyPetProfileImage(Long petId, MultipartFile file, Long memberId) throws IOException {
        Pet pet = petRepository.findById(petId).orElseThrow(
                () -> new CommonException(PetErrorCode.PET_NOT_FOUND));

        // 기존 파일을 삭제하고 S3에서도 삭제한다,
        String fileNameForS3 = pet.getProfileImageUrl().replace(S3_URL, "");
        s3Service.delete(fileNameForS3);
        imageRepository.deleteImage(pet.getProfileImageUrl(), petId);

        // 새로운 파일을 저장하고 S3에 업로드한다.
        String fileName = s3Service.upload(file, ImageDomain.PET, memberId);
        Image image = Image.from(ImageDomain.PET, S3_URL + fileName, 1, memberId);
        image.modifyDomainId(pet.getId());
        imageRepository.save(image);

        pet.modifyProfileImageUrl(image.getPath());
        return image.getPath();
    }


    // 임시 등록한 강아지의 프로필 이미지를 삭제한다.
    @Transactional
    public void rollbackPetProfileImage(DeletePetProfileImageRequest request) {
        String fileNameForS3 = request.profileImageUrl().replace(S3_URL, "");
        s3Service.delete(fileNameForS3);
        imageRepository.rollbackImage(request.profileImageUrl());
    }


    // 강아지의 프로필 이미지를 삭제한다.
    @Transactional
    public void deletePetProfileImage(Long petId, DeletePetProfileImageRequest request) {
        String fileNameForS3 = request.profileImageUrl().replace(S3_URL, "");
        s3Service.delete(fileNameForS3);
        imageRepository.deleteImage(request.profileImageUrl(), petId);

        Pet pet = petRepository.findById(petId).orElseThrow(
                () -> new CommonException(PetErrorCode.PET_NOT_FOUND));
        pet.modifyProfileImageUrl(null);
    }


    // 강아지를 삭제한다.
    @Transactional
    public void deletePet(Long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(
                () -> new CommonException(PetErrorCode.PET_NOT_FOUND));

        s3Service.delete(pet.getProfileImageUrl().replace(S3_URL, ""));
        imageRepository.deleteImage(pet.getProfileImageUrl(), petId);
        petRepository.deletePet(petId);
    }


    // 강아지 등록 시 중복체크를 진행한다.
    private void checkDuplicatedPet(CreatePetRequest request, Long memberId) {
        if (petRepository.existsByNameAndMemberId(request.name(), memberId)) {
            throw new CommonException(DUPLICATED_PET);
        }
    }

}

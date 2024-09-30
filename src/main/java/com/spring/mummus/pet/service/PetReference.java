//package com.spring.mummus.pet.service;
//
//import com.spring.mummus.exception.exception.PetException;
//import com.spring.mummus.pet.domain.dto.RegisterPetRequest;
//import com.spring.mummus.pet.repository.PetRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import static com.spring.mummus.exception.enums.PetErrorCode.DUPLICATED_PET;
//
//@Service
//@RequiredArgsConstructor
//public class PetReference {
//    private final PetRepository petRepository;
//
//    public void checkDuplicatedPet(RegisterPetRequest registerPetRequest) {
//        if (petRepository.existsByNameAndMemberId(registerPetRequest.getName(), registerPetRequest.getMemberId())) {
//            throw new PetException(DUPLICATED_PET);
//        }
//    }
//}

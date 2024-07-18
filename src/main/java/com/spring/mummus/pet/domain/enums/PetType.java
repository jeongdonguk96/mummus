package com.spring.mummus.pet.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PetType {
    POODLE("푸들"),
    MALTESE("말티즈"),
    POMERANIAN("포메라니안"),
    GOLDEN_RETRIEVER("골든 리트리버"),
    SHIVA_INU("시바"),
    DACHSHUND("닥스훈트"),
    DALMATIAN("달마시안"),
    DOBERMANN("도베르만"),
    COLLIE("콜리"),
    BULLDOG("불독"),
    BEAGLE("비글"),
    BICHON_FRISE("비숑 프리제"),
    SAMOYED("사모예드"),
    SHIHTZU("시츄"),
    HUSKY("허스키"),
    WELSH_CORGI("웰시 코기"),
    JINDO_DOG("진돗개"),
    CHIHUAHUA("치와와"),
    PUG("퍼그"),
    ;

    private final String petType;
}

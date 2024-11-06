package com.spring.mummus.pet.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PetType {
    BEAGLE("비글"),
    BICHON_FRISE("비숑 프리제"),
    BULLDOG("불독"),
    CHIHUAHUA("치와와"),
    COLLIE("콜리"),
    DACHSHUND("닥스훈트"),
    DALMATIAN("달마시안"),
    DOBERMANN("도베르만"),
    GOLDEN_RETRIEVER("골든 리트리버"),
    HUSKY("허스키"),
    JINDO_DOG("진돗개"),
    MALTESE("말티즈"),
    POMERANIAN("포메라니안"),
    POODLE("푸들"),
    PUG("퍼그"),
    SAMOYED("사모예드"),
    SHIHTZU("시츄"),
    SHIVA_INU("시바"),
    WELSH_CORGI("웰시 코기"),
    OTHER("기타")
    ;

    private final String petType;
}

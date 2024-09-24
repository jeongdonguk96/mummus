package com.spring.mummus.pet.domain.entity;


import com.spring.mummus.common.BaseEntity;
import com.spring.mummus.pet.domain.enums.Gender;
import com.spring.mummus.pet.domain.enums.PetType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Pet extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private String birth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private PetType petType; // 견종
    private Long memberId;
}

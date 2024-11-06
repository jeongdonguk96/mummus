package com.spring.mummus.fixture;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.pet.enums.Gender;
import com.spring.mummus.pet.enums.PetType;

public class PetFixture {

    private final static FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .defaultNotNull(true)
            .plugin(new JakartaValidationPlugin())
            .build();


    public static Pet createPet(Long id, Long memberId) {
        return Pet.builder()
                .id(id)
                .memberId(memberId)
                .build();
    }


    public static Pet createPet(Long id, Long memberId, String name, String profileImageUrl) {
        return Pet.builder()
                .id(id)
                .memberId(memberId)
                .name(name)
                .profileImageUrl(profileImageUrl)
                .build();
    }


    public static Pet createPet(Long id, Long memberId, String name, String profileImageUrl, int age) {
        return Pet.builder()
                .id(id)
                .memberId(memberId)
                .name(name)
                .profileImageUrl(profileImageUrl)
                .age(age)
                .build();
    }


    public static Pet createPet(Long id, Long memberId, String name, String profileImageUrl, String birth) {
        return Pet.builder()
                .id(id)
                .memberId(memberId)
                .name(name)
                .profileImageUrl(profileImageUrl)
                .birth(birth)
                .build();
    }


    public static Pet createPet(Long id, Long memberId, String name, String profileImageUrl, Gender gender) {
        return Pet.builder()
                .id(id)
                .memberId(memberId)
                .name(name)
                .profileImageUrl(profileImageUrl)
                .gender(gender)
                .build();
    }


    public static Pet createPet(Long id, Long memberId, String name, String profileImageUrl, PetType petType) {
        return Pet.builder()
                .id(id)
                .memberId(memberId)
                .name(name)
                .profileImageUrl(profileImageUrl)
                .petType(petType)
                .build();
    }

}

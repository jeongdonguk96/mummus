package com.spring.mummus.fixture;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import com.spring.mummus.pet.entity.Pet;

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


    public static Pet createPet(Long id, Long memberId, String name) {
        return Pet.builder()
                .id(id)
                .memberId(memberId)
                .name(name)
                .build();
    }

}

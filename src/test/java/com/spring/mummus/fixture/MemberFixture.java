package com.spring.mummus.fixture;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import com.spring.mummus.member.entity.Member;

public class MemberFixture {

    private final static FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .defaultNotNull(true)
            .plugin(new JakartaValidationPlugin())
            .build();;


    public static Member createMember(Long id) {
        return fixtureMonkey.giveMeBuilder(Member.class)
                .set("id", id)
                .sample();
    }

}

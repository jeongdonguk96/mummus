package com.spring.mummus.common;

import com.spring.mummus.follow.repository.FollowRepository;
import com.spring.mummus.follow.service.FollowService;
import com.spring.mummus.member.repository.MemberRepository;
import com.spring.mummus.member.service.MemberService;
import com.spring.mummus.pet.repository.PetRepository;
import com.spring.mummus.pet.service.PetService;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@SpringBootTest
public abstract class AbstractTest implements InitializingBean {

    @Autowired JdbcTemplate jdbcTemplate;
    @Autowired EntityManager em;

    @Autowired public PetService petService;
    @Autowired public MemberService memberService;
    @Autowired public FollowService followService;
    @Autowired public PetRepository petRepository;
    @Autowired public MemberRepository memberRepository;
    @Autowired public FollowRepository followRepository;

    private List<String> entities;


    @Override
    public void afterPropertiesSet() {
        entities = em.getMetamodel().getEntities().stream()
                .filter(entity -> entity.getJavaType().isAnnotationPresent(Entity.class))
                .map(entity -> entity.getJavaType().getSimpleName().toLowerCase())
                .collect(Collectors.toList());
    }


    @AfterEach
    void cleanUp() {
        // 1. 영속성 컨텍스트에 남겨진 SQL을 실행한다.
        em.flush();

        // 2. 테이블 간의 참조 무결성을 비활성화한다.
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

        // 3. 테이블들을 ID 값을 1로 초기화해준다.
        for (String entity : entities) {
            em.createNativeQuery("TRUNCATE TABLE " + entity).executeUpdate();
            em.createNativeQuery("ALTER TABLE " + entity + " AUTO_INCREMENT = 1").executeUpdate();
        }

        // 4. 참조 무결성을 활성화한다.
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }

}

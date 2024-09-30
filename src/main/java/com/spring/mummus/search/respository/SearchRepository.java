package com.spring.mummus.search.respository;

import com.spring.mummus.search.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRepository extends JpaRepository<Search, Long>, SearchRepositoryQuerydsl {
}

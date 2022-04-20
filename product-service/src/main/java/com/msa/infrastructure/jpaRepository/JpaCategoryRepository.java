package com.msa.infrastructure.jpaRepository;

import com.msa.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCategoryRepository extends JpaRepository<Category,Long> {
    boolean existsByName(String name);
}

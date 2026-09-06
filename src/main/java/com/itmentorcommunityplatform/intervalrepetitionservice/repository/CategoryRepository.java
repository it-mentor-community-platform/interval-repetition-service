package com.itmentorcommunityplatform.intervalrepetitionservice.repository;

import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Category;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.CategoryWithSpecializationName;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByNameAndSpecializationId(String name, Long specializationId);

    List<Category> findBySpecializationId(Long specializationId);

    @Query("""
                 SELECT c.id, s.name AS specialization_name, c.name
                 FROM categories c
                 JOIN specializations s ON s.id = c.specialization_id
                 WHERE c.id = :categoryId;
            """)
    Optional<CategoryWithSpecializationName> findCategoryWithSpecializationNameById(Long categoryId);

}

package com.itmentorcommunityplatform.intervalrepetitionservice.repository;

import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoriesRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByNameAndSpecializationId(String name, Long specializationId);

}

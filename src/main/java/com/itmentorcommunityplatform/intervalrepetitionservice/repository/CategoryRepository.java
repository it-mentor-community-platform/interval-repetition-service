package com.itmentorcommunityplatform.intervalrepetitionservice.repository;

import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriesRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByNameAndSpecializationId(String name, Long specializationId);

    List<Category> findBySpecializationId(Long specializationId);

}

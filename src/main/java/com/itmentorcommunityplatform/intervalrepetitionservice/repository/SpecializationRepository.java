package com.itmentorcommunityplatform.intervalrepetitionservice.repository;

import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Specialization;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SpecializationRepository extends CrudRepository<Specialization, Long> {

        Optional<Specialization> findByName(String specializationName);

}

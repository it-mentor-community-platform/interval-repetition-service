package com.itmentorcommunityplatform.intervalrepetitionservice.repository;

import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    Optional<Question> findByTitle(String title);

}

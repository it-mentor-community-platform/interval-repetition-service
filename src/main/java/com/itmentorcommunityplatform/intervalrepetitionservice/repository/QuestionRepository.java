package com.itmentorcommunityplatform.intervalrepetitionservice.repository;

import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Question;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    Optional<Question> findByTitle(String title);

    @Query("""
        SELECT COUNT(*)
        FROM questions q
        WHERE q.category_id = :categoryId
          AND q.enabled = true
          AND NOT EXISTS (
              SELECT 1
                      FROM user_question_schedules uqs
              WHERE uqs.question_id = q.id
                AND uqs.user_id = :userId
          )
    """)
    Integer countNewByCategoryId(Long categoryId, Long userId);

    @Query("""
        SELECT COUNT(*)
        FROM questions q
                JOIN user_question_schedules uqs
          ON uqs.question_id = q.id
        WHERE q.category_id = :categoryId
          AND q.enabled = true
          AND uqs.user_id = :userId
          AND uqs.next_review_at <= :currentTime
    """)
    Integer countReadyForRepetitionByCategoryId(Long categoryId, Long userId, Long currentTime);


    @Query("""
        SELECT COUNT(*)
        FROM questions
        WHERE category_id = :categoryId
          AND enabled = true
    """)
    Integer countAllByCategoryId(Long categoryId);

}

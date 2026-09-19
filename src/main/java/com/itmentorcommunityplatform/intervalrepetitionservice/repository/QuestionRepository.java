package com.itmentorcommunityplatform.intervalrepetitionservice.repository;

import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Question;
import com.itmentorcommunityplatform.intervalrepetitionservice.projection.NextQuestion;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    Optional<Question> findByTitle(String title);

    List<Question> findAllByCategoryId(Long categoryId);


    @Query("""
                SELECT  q.id,
                        q.category_id,
                        q.title,
                        q.answer,
                        COUNT(*) OVER () - 1 AS questions_left
                FROM questions q
                JOIN user_category_selections ucs
                    ON ucs.category_id = q.category_id
                        AND ucs.user_id = :userId
                LEFT JOIN user_question_schedules uqs
                        ON uqs.question_id = q.id
                            AND uqs.user_id = :userId
                WHERE (uqs.question_id IS NULL
                        OR uqs.next_review_at <= EXTRACT(EPOCH FROM now()))
                        AND q.enabled
                ORDER BY uqs.next_review_at NULLS FIRST
                LIMIT 1;
            """)
    Optional<NextQuestion> getNextQuestionForRepetitionBySelectedCategories(Long userId);


    @Query("""
                SELECT  q.id,
                        q.category_id,
                        q.title,
                        q.answer,
                        COUNT(*) OVER () - 1 AS questions_left
                FROM questions q
                LEFT JOIN user_question_schedules uqs
                    ON uqs.question_id = q.id
                   AND uqs.user_id = :userId
                WHERE q.category_id = :categoryId
                  AND (
                      uqs.question_id IS NULL
                      OR uqs.next_review_at <= EXTRACT(EPOCH FROM now())
                  )AND q.enabled
                ORDER BY uqs.next_review_at NULLS FIRST
                LIMIT 1;
            """)
    Optional<NextQuestion> getNextQuestionForRepetitionByCategoryId(Long userId, Long categoryId);

}

package com.itmentorcommunityplatform.intervalrepetitionservice.repository;

import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Category;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.CategoryWithSpecializationName;
import com.itmentorcommunityplatform.intervalrepetitionservice.projection.CategoryWithStatistics;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends ListCrudRepository<Category, Long> {

    Optional<Category> findByNameAndSpecializationId(String name, Long specializationId);

    @Query("""
                SELECT
                    c.id AS id,
                    c.name AS name,
                    s.name AS specialization_name,
                    ucs.category_id IS NOT NULL AS selected,
                    count(q.id) AS all_questions,
                    count(q.id) AS new_questions,
                    count(q.id) FILTER (WHERE uqs.next_review_at <= EXTRACT(EPOCH FROM now())) AS questions_ready_to_repeat
                FROM categories c
                LEFT JOIN interval_repetition_service.user_category_selections ucs
                    ON c.id = ucs.category_id
                        AND ucs.user_id = :userId
                JOIN interval_repetition_service.questions q
                    ON c.id = q.category_id
                        AND q.enabled = true
                LEFT JOIN interval_repetition_service.user_question_schedules uqs
                    ON q.id = uqs.question_id
                        AND uqs.user_id = :userId
                JOIN specializations s ON c.specialization_id = s.id
                WHERE c.specialization_id = :specializationId
                GROUP BY c.id, ucs.category_id, s.name
                ORDER BY c.id;
            """)
    List<CategoryWithStatistics> findCategoriesWithStatisticBySpecializationId(Long specializationId, Long userId);


    @Query("""
                SELECT
                    c.id AS id,
                    c.name AS name,
                    s.name AS specialization_name,
                    ucs.category_id IS NOT NULL AS selected,
                    count(q.id) AS all_questions,
                    count(q.id) AS new_questions,
                    count(q.id) FILTER (WHERE uqs.next_review_at <= EXTRACT(EPOCH FROM now())) AS questions_ready_to_repeat
                FROM categories c
                LEFT JOIN interval_repetition_service.user_category_selections ucs
                    ON c.id = ucs.category_id
                        AND ucs.user_id = :userId
                JOIN interval_repetition_service.questions q
                    ON c.id = q.category_id
                        AND q.enabled = true
                LEFT JOIN interval_repetition_service.user_question_schedules uqs
                    ON q.id = uqs.question_id
                        AND uqs.user_id = :userId
                JOIN specializations s ON c.specialization_id = s.id
                WHERE ucs.category_id IS NOT NULL
                GROUP BY c.id, ucs.category_id, s.name
                ORDER BY c.id;
            """)
    List<CategoryWithStatistics> findAllSelectedCategoriesWithStatistic(Long userId);



    @Query("""
                 SELECT c.id, s.name AS specialization_name, c.name
                 FROM categories c
                 JOIN specializations s ON s.id = c.specialization_id
            """)
    List<CategoryWithSpecializationName> findAllCategoriesWithSpecializationName();

}

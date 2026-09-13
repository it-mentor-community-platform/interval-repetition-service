package com.itmentorcommunityplatform.intervalrepetitionservice.repository;

import com.itmentorcommunityplatform.intervalrepetitionservice.entity.UserQuestionSchedule;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserQuestionScheduleRepository extends CrudRepository<UserQuestionSchedule, Long> {

    Optional<UserQuestionSchedule> findUserQuestionScheduleByUserIdAndQuestionId(Long userId, Long questionId);

    @Query("""
             INSERT INTO user_question_schedules (
                 user_id,
                 question_id,
                 successful_repetitions,
                 ease_factor,
                 "interval",
                 next_review_at,
                 last_review_at
             )
             VALUES (
                 :#{#userQuestionSchedule.userId},
                 :#{#userQuestionSchedule.questionId},
                 :#{#userQuestionSchedule.successfulRepetitions},
                 :#{#userQuestionSchedule.easeFactor},
                 :#{#userQuestionSchedule.interval},
                 :#{#userQuestionSchedule.nextReviewAt},
                 :#{#userQuestionSchedule.lastReviewAt}
             )
             ON CONFLICT (user_id, question_id)
             DO UPDATE SET
                 successful_repetitions = EXCLUDED.successful_repetitions,
                 ease_factor = EXCLUDED.ease_factor,
                 "interval" = EXCLUDED."interval",
                 next_review_at = EXCLUDED.next_review_at,
                 last_review_at = EXCLUDED.last_review_at
              RETURNING  id,
                         user_id,
                         question_id,
                         successful_repetitions,
                         ease_factor,
                         "interval",
                         next_review_at,
                         last_review_at;
         """)
    UserQuestionSchedule upsertUserQuestionSchedule(UserQuestionSchedule userQuestionSchedule);

}

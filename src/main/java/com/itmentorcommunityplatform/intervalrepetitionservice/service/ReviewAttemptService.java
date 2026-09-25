package com.itmentorcommunityplatform.intervalrepetitionservice.service;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.ReviewAttemptDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.ReviewAttempt;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.UserQuestionSchedule;
import com.itmentorcommunityplatform.intervalrepetitionservice.repository.ReviewAttemptRepository;
import com.itmentorcommunityplatform.intervalrepetitionservice.repository.UserQuestionScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ReviewAttemptService {

    private final ReviewAttemptRepository reviewAttemptRepository;
    private final UserQuestionScheduleRepository userQuestionScheduleRepository;

    private final QuestionService questionService;

    @Transactional
    public void saveReviewAttempt(ReviewAttemptDto reviewAttempt, Long userId) {

        questionService.checkIfExists(reviewAttempt.questionId());

        Optional<UserQuestionSchedule> scheduleOptional = userQuestionScheduleRepository.findUserQuestionScheduleByUserIdAndQuestionId(userId, reviewAttempt.questionId());

        UserQuestionSchedule oldSchedule = scheduleOptional
                .orElseGet(() -> new UserQuestionSchedule(userId, reviewAttempt.questionId()));

        UserQuestionSchedule newSchedule = userQuestionScheduleRepository
                .upsertUserQuestionSchedule(
                        recalculateUserQuestionSchedule(oldSchedule, reviewAttempt.quality())
                );

        reviewAttemptRepository.save(buildReviewAttempt(oldSchedule, newSchedule, reviewAttempt.quality()));

    }

    /**
     * Recalculates the user's question schedule after a review attempt.
     *
     * <p>The number of successful repetitions is increased when the answer
     * quality is at least 3. For lower quality values, the successful
     * repetition counter is reset to zero.</p>
     *
     * <p>The ease factor is recalculated according to the SM-2 algorithm,
     * and the next repetition interval is calculated using the previous
     * ease factor.</p>
     *
     * @param oldSchedule previous schedule of the question
     * @param quality answer quality, from 0 to 5
     * @return recalculated schedule for the next repetition
     */
    private UserQuestionSchedule recalculateUserQuestionSchedule(UserQuestionSchedule oldSchedule, int quality) {


        int successfulRepetitions = quality >= 3 ? oldSchedule.getSuccessfulRepetitions() + 1 : 0;
        double easeFactor = calculateEasyFactor(oldSchedule.getEaseFactor(), quality);
        int interval = calculateInterval(successfulRepetitions, oldSchedule.getEaseFactor());
        long nextReviewAt = Instant.now()
                .plus(Duration.ofDays(interval))
                .plusSeconds(ThreadLocalRandom.current().nextLong(3600))
                .getEpochSecond();
        long lastReviewAt = Instant.now().getEpochSecond();


        return UserQuestionSchedule.builder()
                .userId(oldSchedule.getUserId())
                .questionId(oldSchedule.getQuestionId())
                .successfulRepetitions(successfulRepetitions)
                .easeFactor(easeFactor)
                .interval(interval)
                .nextReviewAt(nextReviewAt)
                .lastReviewAt(lastReviewAt)
                .build();
    }

    private ReviewAttempt buildReviewAttempt(UserQuestionSchedule oldSchedule, UserQuestionSchedule newSchedule, int quality) {

        return ReviewAttempt.builder()
                .userQuestionScheduleId(newSchedule.getId())
                .quality(quality)
                .easeFactor(oldSchedule.getEaseFactor())
                .newEaseFactor(newSchedule.getEaseFactor())
                .answeredAt(newSchedule.getLastReviewAt())
                .build();
    }

    /**
     * Calculates the new ease factor according to the SM-2 algorithm.
     *
     * <p>The ease factor is adjusted based on the quality of the answer
     * using the SM-2 formula. The resulting value cannot be lower than
     * the minimum ease factor of 1.3.</p>
     *
     * <p>The formula is:</p>
     *
     * <pre>
     * EF' = EF + (0.1 - (5 - q) * (0.08 + (5 - q) * 0.02))
     * </pre>
     *
     * <p>where {@code EF} is the current ease factor and {@code q} is
     * the answer quality from 0 to 5.</p>
     *
     * @param ef current ease factor
     * @param quality answer quality, from 0 to 5
     * @return recalculated ease factor, with a minimum value of 1.3
     */
    private double calculateEasyFactor(double ef, int quality) {
        ef = ef + (0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02));
        return Math.max(ef, 1.3);
    }


    /**
     * Calculates the repetition interval according to the SM-2 algorithm.
     *
     * <p>For the first two successful repetitions, fixed intervals of
     * 1 and 6 days are used. For subsequent repetitions, the previous
     * interval is multiplied by the current ease factor and rounded
     * to the nearest whole number.</p>
     *
     * <p>The ease factor passed to this method is the ease factor from
     * the previous schedule state.</p>
     *
     * @param repetitionsCount number of successful repetitions
     * @param ef ease factor used to calculate the interval
     * @return repetition interval in days
     */
    private int calculateInterval(int repetitionsCount, double ef) {
        return switch (repetitionsCount) {
            case 0, 1 -> 1;
            case 2 -> 6;
            default -> (int) Math.round(calculateInterval(repetitionsCount - 1, ef) * ef);
        };
    }

}

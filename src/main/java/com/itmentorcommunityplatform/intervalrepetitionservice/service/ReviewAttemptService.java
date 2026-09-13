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
                .orElseGet(() -> initiateUserQuestionSchedule(userId, reviewAttempt.questionId()));

        UserQuestionSchedule newSchedule = userQuestionScheduleRepository
                .upsertUserQuestionSchedule(
                        recalculateUserQuestionSchedule(oldSchedule, reviewAttempt.quality())
                );

        reviewAttemptRepository.save(buildReviewAttempt(oldSchedule, newSchedule, reviewAttempt.quality()));

    }

    private UserQuestionSchedule recalculateUserQuestionSchedule(UserQuestionSchedule oldSchedule, int quality) {


        int successfulRepetitions = quality > 3 ? oldSchedule.getSuccessfulRepetitions() + 1 : 0;
        double easeFactor = calculateEasyFactor(oldSchedule.getEaseFactor(), quality);
        int interval = calculateInterval(successfulRepetitions, easeFactor);
        long nextReviewAt = Instant.now().plus(Duration.ofDays(interval)).getEpochSecond();
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

    private UserQuestionSchedule initiateUserQuestionSchedule(Long userId, Long questionId) {

        return UserQuestionSchedule.builder()
                .userId(userId)
                .questionId(questionId)
                .successfulRepetitions(0)
                .easeFactor(2.5)
                .interval(1)
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

    private double calculateEasyFactor(double ef, int quality) {
        ef = ef + (0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02));
        return Math.max(ef, 1.3);
    }

    private int calculateInterval(int repetitionsCount, double ef) {
        return switch (repetitionsCount) {
            case 0, 1 -> 1;
            case 2 -> 6;
            default -> (int) Math.round(calculateInterval(repetitionsCount - 1, ef) * ef);
        };
    }

}

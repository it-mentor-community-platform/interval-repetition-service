package com.itmentorcommunityplatform.intervalrepetitionservice;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.ReviewAttemptDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.ReviewAttempt;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.UserQuestionSchedule;
import com.itmentorcommunityplatform.intervalrepetitionservice.repository.ReviewAttemptRepository;
import com.itmentorcommunityplatform.intervalrepetitionservice.repository.UserQuestionScheduleRepository;
import com.itmentorcommunityplatform.intervalrepetitionservice.service.QuestionService;
import com.itmentorcommunityplatform.intervalrepetitionservice.service.ReviewAttemptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests the main scenarios of the interval repetition algorithm:
 * <ul>
 *     <li>creation of the first successful repetition;</li>
 *     <li>calculation of the second repetition interval;</li>
 *     <li>calculation of subsequent intervals using the previous ease factor;</li>
 *     <li>reset of successful repetitions after an unsuccessful answer;</li>
 *     <li>calculation of the new ease factor according to SM-2;</li>
 *     <li>enforcement of the minimum ease factor value of 1.3.</li>
 * </ul>
 */
@ExtendWith(MockitoExtension.class)
class ReviewAttemptServiceTest {

    @Mock
    private ReviewAttemptRepository reviewAttemptRepository;

    @Mock
    private UserQuestionScheduleRepository userQuestionScheduleRepository;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private ReviewAttemptService reviewAttemptService;

    private Long userId;
    private Long questionId;

    private ReviewAttemptDto reviewAttempt;

    private UserQuestionSchedule oldSchedule;

    @BeforeEach
    void setUp() {
        userId = 1L;
        questionId = 10L;

        reviewAttempt = new ReviewAttemptDto(
                questionId,
                5
        );

        oldSchedule = UserQuestionSchedule.builder()
                .id(100L)
                .userId(userId)
                .questionId(questionId)
                .successfulRepetitions(0)
                .easeFactor(2.5)
                .interval(0)
                .build();

        doNothing()
                .when(questionService)
                .checkIfExists(questionId);
    }

    @Test
    void shouldCreateFirstSuccessfulRepetition() {
        when(userQuestionScheduleRepository
                .findUserQuestionScheduleByUserIdAndQuestionId(userId, questionId))
                .thenReturn(Optional.empty());

        UserQuestionSchedule savedSchedule = UserQuestionSchedule.builder()
                .id(100L)
                .userId(userId)
                .questionId(questionId)
                .successfulRepetitions(1)
                .easeFactor(2.6)
                .interval(1)
                .lastReviewAt(123L)
                .nextReviewAt(123L)
                .build();

        when(userQuestionScheduleRepository.upsertUserQuestionSchedule(any()))
                .thenReturn(savedSchedule);

        reviewAttemptService.saveReviewAttempt(reviewAttempt, userId);

        ArgumentCaptor<UserQuestionSchedule> captor =
                ArgumentCaptor.forClass(UserQuestionSchedule.class);

        verify(userQuestionScheduleRepository)
                .upsertUserQuestionSchedule(captor.capture());

        UserQuestionSchedule result = captor.getValue();

        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getQuestionId()).isEqualTo(questionId);
        assertThat(result.getSuccessfulRepetitions()).isEqualTo(1);
        assertThat(result.getEaseFactor()).isEqualTo(2.6);
        assertThat(result.getInterval()).isEqualTo(1);

        verify(reviewAttemptRepository).save(any(ReviewAttempt.class));
    }

    @Test
    void shouldSetSecondIntervalToSixDays() {
        oldSchedule.setSuccessfulRepetitions(1);

        when(userQuestionScheduleRepository
                .findUserQuestionScheduleByUserIdAndQuestionId(userId, questionId))
                .thenReturn(Optional.of(oldSchedule));

        when(userQuestionScheduleRepository.upsertUserQuestionSchedule(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        reviewAttemptService.saveReviewAttempt(reviewAttempt, userId);

        ArgumentCaptor<UserQuestionSchedule> captor =
                ArgumentCaptor.forClass(UserQuestionSchedule.class);

        verify(userQuestionScheduleRepository)
                .upsertUserQuestionSchedule(captor.capture());

        UserQuestionSchedule result = captor.getValue();

        assertThat(result.getSuccessfulRepetitions()).isEqualTo(2);
        assertThat(result.getInterval()).isEqualTo(6);
    }

    @Test
    void shouldCalculateIntervalUsingOldEaseFactor() {
        oldSchedule.setSuccessfulRepetitions(2);
        oldSchedule.setEaseFactor(2.5);

        when(userQuestionScheduleRepository
                .findUserQuestionScheduleByUserIdAndQuestionId(userId, questionId))
                .thenReturn(Optional.of(oldSchedule));

        when(userQuestionScheduleRepository.upsertUserQuestionSchedule(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        reviewAttemptService.saveReviewAttempt(reviewAttempt, userId);

        ArgumentCaptor<UserQuestionSchedule> captor =
                ArgumentCaptor.forClass(UserQuestionSchedule.class);

        verify(userQuestionScheduleRepository)
                .upsertUserQuestionSchedule(captor.capture());

        UserQuestionSchedule result = captor.getValue();

        /*
         * Старый EF = 2.5
         * Новое количество успешных повторений = 3
         *
         * 6 * 2.5 = 15
         *
         * Новый EF после quality=5 будет 2.6,
         * но для расчёта интервала используется старый EF = 2.5.
         */
        assertThat(result.getSuccessfulRepetitions()).isEqualTo(3);
        assertThat(result.getEaseFactor()).isEqualTo(2.6);
        assertThat(result.getInterval()).isEqualTo(15);
    }

    @Test
    void shouldResetSuccessfulRepetitionsAfterFailedAnswer() {
        oldSchedule.setSuccessfulRepetitions(5);

        reviewAttempt = new ReviewAttemptDto(
                questionId,
                2
        );

        when(userQuestionScheduleRepository
                .findUserQuestionScheduleByUserIdAndQuestionId(userId, questionId))
                .thenReturn(Optional.of(oldSchedule));

        when(userQuestionScheduleRepository.upsertUserQuestionSchedule(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        reviewAttemptService.saveReviewAttempt(reviewAttempt, userId);

        ArgumentCaptor<UserQuestionSchedule> captor =
                ArgumentCaptor.forClass(UserQuestionSchedule.class);

        verify(userQuestionScheduleRepository)
                .upsertUserQuestionSchedule(captor.capture());

        UserQuestionSchedule result = captor.getValue();

        assertThat(result.getSuccessfulRepetitions()).isZero();
        assertThat(result.getInterval()).isEqualTo(1);
    }

    @Test
    void shouldCalculateNewEaseFactorAccordingToSm2() {
        reviewAttempt = new ReviewAttemptDto(
                questionId,
                4
        );

        when(userQuestionScheduleRepository
                .findUserQuestionScheduleByUserIdAndQuestionId(userId, questionId))
                .thenReturn(Optional.of(oldSchedule));

        when(userQuestionScheduleRepository.upsertUserQuestionSchedule(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        reviewAttemptService.saveReviewAttempt(reviewAttempt, userId);

        ArgumentCaptor<UserQuestionSchedule> captor =
                ArgumentCaptor.forClass(UserQuestionSchedule.class);

        verify(userQuestionScheduleRepository)
                .upsertUserQuestionSchedule(captor.capture());

        UserQuestionSchedule result = captor.getValue();

        /*
         * EF = 2.5
         * quality = 4
         *
         * 2.5 + (0.1 - (5 - 4) * (0.08 + (5 - 4) * 0.02))
         * = 2.5 + (0.1 - 0.1)
         * = 2.5
         */
        assertThat(result.getEaseFactor()).isEqualTo(2.5);
    }

    @Test
    void shouldNotAllowEaseFactorToBeLessThanOnePointThree() {
        oldSchedule.setEaseFactor(1.3);

        reviewAttempt = new ReviewAttemptDto(
                questionId,
                0
        );

        when(userQuestionScheduleRepository
                .findUserQuestionScheduleByUserIdAndQuestionId(userId, questionId))
                .thenReturn(Optional.of(oldSchedule));

        when(userQuestionScheduleRepository.upsertUserQuestionSchedule(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        reviewAttemptService.saveReviewAttempt(reviewAttempt, userId);

        ArgumentCaptor<UserQuestionSchedule> captor =
                ArgumentCaptor.forClass(UserQuestionSchedule.class);

        verify(userQuestionScheduleRepository)
                .upsertUserQuestionSchedule(captor.capture());

        UserQuestionSchedule result = captor.getValue();

        assertThat(result.getEaseFactor()).isEqualTo(1.3);
    }




}

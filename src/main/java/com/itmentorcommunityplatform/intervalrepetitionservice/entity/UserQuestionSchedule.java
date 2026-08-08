package com.itmentorcommunityplatform.intervalrepetitionservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("user_question_schedules")
public class UserQuestionSchedule {

    @Id
    private Long id;

    private Long userId;

    private Long questionId;

    private Integer successfulRepetitions;

    private Double easeFactor;

    private Integer interval;

    private Long nextReviewAt;

    private Long lastReviewAt;
}

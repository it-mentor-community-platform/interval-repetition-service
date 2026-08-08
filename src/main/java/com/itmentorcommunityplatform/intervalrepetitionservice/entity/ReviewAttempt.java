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
@Table("review_attempts")
public class ReviewAttempt {

    @Id
    private Long id;

    private Long userQuestionScheduleId;

    private Integer quality;

    private Double easeFactor;

    private Double newEaseFactor;

    private Long answeredAt;
}

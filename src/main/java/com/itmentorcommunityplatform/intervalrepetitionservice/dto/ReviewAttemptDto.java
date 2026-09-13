package com.itmentorcommunityplatform.intervalrepetitionservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReviewAttemptDto(

        @NotNull(message = "Question id must not be null")
        Long questionId,

        @NotNull(message = "Quality must not be null")
        @Min(value = 0, message = "Quality must be at least 0")
        @Max(value = 5, message = "Quality must be at max 5")
        Integer quality

) {
}

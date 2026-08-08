package com.itmentorcommunityplatform.intervalrepetitionservice.dto;

import jakarta.validation.constraints.NotBlank;

public record QuestionRequestDto(

        @NotBlank
        String specialization,

        @NotBlank
        String category,

        @NotBlank
        String title,

        @NotBlank
        String answer
) {
}

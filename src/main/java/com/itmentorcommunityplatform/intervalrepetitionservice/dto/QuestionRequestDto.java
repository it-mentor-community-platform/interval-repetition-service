package com.itmentorcommunityplatform.intervalrepetitionservice.dto;

import jakarta.validation.constraints.NotBlank;

public record QuestionRequestDto(

        @NotBlank(message = "Specialization must not be blank")
        String specialization,

        @NotBlank(message = "Category must not be blank")
        String category,

        @NotBlank(message = "Title must not be blank")
        String title,

        @NotBlank(message = "Answer must not be blank")
        String answer
) {
}

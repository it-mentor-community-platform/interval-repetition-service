package com.itmentorcommunityplatform.intervalrepetitionservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SelectedCategoryResponseDto(

        Long id,

        String name,

        String specialization,

        @JsonProperty("new_questions")
        Integer newQuestions,

        @JsonProperty("questions_ready_to_repeat")
        Integer questionsReadyToRepeat,

        @JsonProperty("all_questions")
        Integer allQuestions


) {
}

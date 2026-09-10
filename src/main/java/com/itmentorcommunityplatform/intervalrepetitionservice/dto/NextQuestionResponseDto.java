package com.itmentorcommunityplatform.intervalrepetitionservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NextQuestionResponseDto(


        Long questionId,

        Long categoryId,

        String title,

        String answer,

        @JsonProperty("questions_left")
        Integer questionsLeft

) {
}

package com.itmentorcommunityplatform.intervalrepetitionservice.dto;

public record CategoryResponseDto(

        Long id,

        String name,

        Boolean selected,

        Integer newQuestions,

        Integer questionsReadyToRepeat,

        Integer allQuestions

) {
}

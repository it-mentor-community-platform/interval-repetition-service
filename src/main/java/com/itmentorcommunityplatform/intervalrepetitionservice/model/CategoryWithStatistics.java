package com.itmentorcommunityplatform.intervalrepetitionservice.model;

public record CategoryWithStatistics(
        Long id,

        String name,

        String specializationName,

        Boolean selected,

        Integer newQuestions,

        Integer questionsReadyToRepeat,

        Integer allQuestions

) {
}

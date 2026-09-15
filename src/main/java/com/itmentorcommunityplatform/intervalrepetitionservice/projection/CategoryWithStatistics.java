package com.itmentorcommunityplatform.intervalrepetitionservice.projection;

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

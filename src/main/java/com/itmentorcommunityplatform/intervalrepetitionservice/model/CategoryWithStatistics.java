package com.itmentorcommunityplatform.intervalrepetitionservice.model;

public record CategoryWithStatistics(
        Long id,

        String name,

        Long specializationId,

        Boolean selected,

        Integer newQuestions,

        Integer questionsReadyToRepeat,

        Integer allQuestions

) {
}

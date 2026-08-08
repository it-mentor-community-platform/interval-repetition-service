package com.itmentorcommunityplatform.intervalrepetitionservice.dto;

public record QuestionInsertInternalResponseDto(
        Question question,
        Category category,
        Specialization specialization
) {

    public record Question(
            Long id,
            Long categoryId,
            String title,
            String answer,
            boolean enabled
    ) {
    }

    public record Category(
            Long id,
            Long specializationId,
            String name
    ) {
    }

    public record Specialization(
            Long id,
            String name
    ) {
    }
}

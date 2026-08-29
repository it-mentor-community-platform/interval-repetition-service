package com.itmentorcommunityplatform.intervalrepetitionservice.dto;

public record CategoryResponseDto(

        Long id,

        String name,

        Boolean selected,

        Integer new_questions,

        Integer questions_ready_to_repeat,

        Integer all_questions

) {
}

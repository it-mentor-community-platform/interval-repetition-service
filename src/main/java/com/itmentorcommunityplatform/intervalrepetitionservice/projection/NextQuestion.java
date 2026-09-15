package com.itmentorcommunityplatform.intervalrepetitionservice.projection;

public record NextQuestion(

        Long id,

        Long categoryId,

        String title,

        String answer,

        Integer questionsLeft

) {
}
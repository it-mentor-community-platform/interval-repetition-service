package com.itmentorcommunityplatform.intervalrepetitionservice.model;

public record NextQuestion(

        Long id,

        Long categoryId,

        String title,

        String answer,

        Integer questionsLeft

) {
}
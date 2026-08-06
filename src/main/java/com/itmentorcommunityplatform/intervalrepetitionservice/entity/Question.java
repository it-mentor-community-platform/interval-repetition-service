package com.itmentorcommunityplatform.intervalrepetitionservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("questions")
public class Question {

    @Id
    private Long id;

    private Long categoryId;

    private String title;

    private String answer;

    private boolean enabled;


    public Question(Long categoryId, String title, String answer, boolean enabled) {
        this.categoryId = categoryId;
        this.title = title;
        this.answer = answer;
        this.enabled = enabled;
    }
}
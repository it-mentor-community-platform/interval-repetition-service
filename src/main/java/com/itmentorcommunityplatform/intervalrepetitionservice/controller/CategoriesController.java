package com.itmentorcommunityplatform.intervalrepetitionservice.controller;

import com.itmentorcommunityplatform.intervalrepetitionservice.docs.GetCategoryWithQuestionsDocs;
import com.itmentorcommunityplatform.intervalrepetitionservice.docs.GetNextQuestionFromCertainCategoryDocs;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.CategoryWithQuestionsResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.NextQuestionResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.service.CategoryService;
import com.itmentorcommunityplatform.intervalrepetitionservice.service.QuestionService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/interval-repetition/categories")
@RequiredArgsConstructor
public class CategoriesController {

    private final QuestionService questionService;
    private final CategoryService categoryService;

    @GetMapping("/{categoryId}/next-question")
    @GetNextQuestionFromCertainCategoryDocs
    public ResponseEntity<NextQuestionResponseDto> getNextQuestionFromCertainCategory(@RequestHeader("X-Telegram-User-Id") Long userId,
                                                                                      @PathVariable
                                                                                      @Positive(message = "Category ID must be positive")
                                                                                      Long categoryId) {

        Optional<NextQuestionResponseDto> nextQuestion = questionService.getNextQuestionFromCertainCategory(userId, categoryId);

        return nextQuestion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());

    }

    @GetMapping("/{categoryId}")
    @GetCategoryWithQuestionsDocs
    public ResponseEntity<CategoryWithQuestionsResponseDto> getCategoryWithQuestions(@RequestHeader("X-Telegram-User-Id") Long userId,
                                                                                     @PathVariable
                                                                                     @Positive(message = "Category ID must be positive")
                                                                                     Long categoryId){

        return ResponseEntity.ok(categoryService.getCategoryWithQuestions(userId, categoryId));

    }


}

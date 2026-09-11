package com.itmentorcommunityplatform.intervalrepetitionservice.controller;

import com.itmentorcommunityplatform.intervalrepetitionservice.docs.DeleteSelectedCategoryDocs;
import com.itmentorcommunityplatform.intervalrepetitionservice.docs.GetNextQuestionFromCertainCategoryDocs;
import com.itmentorcommunityplatform.intervalrepetitionservice.docs.GetSelectedCategoriesDocs;
import com.itmentorcommunityplatform.intervalrepetitionservice.docs.SaveSelectedCategoriesDocs;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.NextQuestionResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.SavedSelectedCategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.SelectedCategoriesRequestDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.SelectedCategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.service.CategoryService;
import com.itmentorcommunityplatform.intervalrepetitionservice.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/interval-repetition/selected-categories")
@RequiredArgsConstructor
public class SelectedCategoriesController{

    private final CategoryService categoryService;
    private final QuestionService questionService;

    @PostMapping
    @SaveSelectedCategoriesDocs
    public ResponseEntity<List<SavedSelectedCategoryResponseDto>> saveSelectedCategories(
            @RequestHeader("X-Telegram-User-Id") Long userId,
            @Valid @RequestBody SelectedCategoriesRequestDto categories) throws URISyntaxException {

        return ResponseEntity
                .created(new URI(""))
                .body(categoryService.saveCategorySelection(userId, categories.categoryIds()));

    }

    @GetMapping
    @GetSelectedCategoriesDocs
    public ResponseEntity<List<SelectedCategoryResponseDto>> getSelectedCategories(@RequestHeader("X-Telegram-User-Id") Long userId) {

        return ResponseEntity.ok(categoryService.getSelectedCategories(userId));
    }

    @DeleteMapping("/{categoryId}")
    @DeleteSelectedCategoryDocs
    public ResponseEntity<Void> deleteSelectedCategory(@RequestHeader("X-Telegram-User-Id") Long userId, @PathVariable Long categoryId) {

        categoryService.deleteSelectedCategory(userId, categoryId);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/next-question")
    public ResponseEntity<NextQuestionResponseDto> getNextQuestionFromSelectedCategories(@RequestHeader("X-Telegram-User-Id") Long userId) {

        Optional<NextQuestionResponseDto> nextQuestion = questionService.getNextQuestionFromSelectedCategories(userId);

        return nextQuestion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());

    }

}
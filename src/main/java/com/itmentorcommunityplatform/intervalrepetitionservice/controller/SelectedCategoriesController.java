package com.itmentorcommunityplatform.intervalrepetitionservice.controller;

import com.itmentorcommunityplatform.intervalrepetitionservice.docs.GetSelectedCategoriesDocs;
import com.itmentorcommunityplatform.intervalrepetitionservice.docs.SaveSelectedCategoriesDocs;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.SavedSelectedCategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.SelectedCategoriesRequestDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.SelectedCategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/interval-repetition/selected-categories")
@RequiredArgsConstructor
public class SelectedCategoriesController{

    private final CategoryService categoryService;

    @PostMapping
    @SaveSelectedCategoriesDocs
    public ResponseEntity<List<SavedSelectedCategoryResponseDto>> saveSelectedCategories(
            @RequestHeader(value = "X-Telegram-User-Id") Long userId,
            @Valid @RequestBody SelectedCategoriesRequestDto categories) throws URISyntaxException {

        return ResponseEntity
                .created(new URI(""))
                .body(categoryService.saveCategorySelection(userId, categories.categoryIds()));

    }

    @GetMapping
    @GetSelectedCategoriesDocs
    public ResponseEntity<List<SelectedCategoryResponseDto>> getSelectedCategories(@RequestHeader(value = "X-Telegram-User-Id") Long userId) {

        return ResponseEntity.ok(categoryService.getSelectedCategories(userId));
    }

}
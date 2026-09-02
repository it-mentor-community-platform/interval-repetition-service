package com.itmentorcommunityplatform.intervalrepetitionservice.service;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.CategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.SavedSelectedCategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Category;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.UserCategorySelection;
import com.itmentorcommunityplatform.intervalrepetitionservice.exception.ResourceAlreadyExistsException;
import com.itmentorcommunityplatform.intervalrepetitionservice.exception.ResourceNotFoundException;
import com.itmentorcommunityplatform.intervalrepetitionservice.mapper.CategoryMapper;
import com.itmentorcommunityplatform.intervalrepetitionservice.model.UserCategoryStatistics;
import com.itmentorcommunityplatform.intervalrepetitionservice.repository.CategoryRepository;
import com.itmentorcommunityplatform.intervalrepetitionservice.repository.QuestionRepository;
import com.itmentorcommunityplatform.intervalrepetitionservice.repository.SpecializationRepository;
import com.itmentorcommunityplatform.intervalrepetitionservice.repository.UserCategorySelectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final QuestionRepository questionRepository;
    private final CategoryRepository categoryRepository;
    private final SpecializationRepository specializationRepository;
    private final UserCategorySelectionRepository selectionRepository;

    private final CategoryMapper categoryMapper;


    public List<CategoryResponseDto> getAllCategoriesBySpecialization(Long userId, Long specializationId) {

        List<Category> categories = categoryRepository.findBySpecializationId(specializationId);

        Set<Long> selectedCategoryIds = selectionRepository.findCategoryIdsByUserId(userId);

        return categories.stream()
                .map(category -> categoryMapper.toResponse(
                        category,
                        selectedCategoryIds.contains(category.getId()),
                        getUserCategoryStatistics(userId, category.getId())
                ))
                .toList();

    }

    @Transactional
    public List<SavedSelectedCategoryResponseDto> saveCategorySelection(Long userId, List<Long> selectedCategoryIds){

        List<UserCategorySelection> categorySelections = selectedCategoryIds.stream()
                .map(id -> new UserCategorySelection(id, userId))
                .toList();
        List<Category> categories = selectedCategoryIds.stream()
                                            .map(this::getCategoryById)
                                            .toList();

        try {
            selectionRepository.saveAll(categorySelections);
        } catch (DbActionExecutionException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                throw new ResourceAlreadyExistsException(
                        "One or more categories are already selected"
                );
            }

            throw e;
        }


        return categories.stream()
                .map(category -> categoryMapper.toSelectedCategoryResponse(
                        category,
                        specializationRepository.findById(category.getSpecializationId()).get().getName()
                ))
                .toList();
    }


    public Category getCategoryById(Long categoryId) {

        return categoryRepository.
                findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category with id " + categoryId + " not found!"));

    }

    private UserCategoryStatistics getUserCategoryStatistics(Long userId, Long categoryId){

        return new UserCategoryStatistics(
                questionRepository.countNewByCategoryId(categoryId, userId),
                questionRepository.countReadyForRepetitionByCategoryId(categoryId, userId, Instant.now().toEpochMilli()),
                questionRepository.countAllByCategoryId(categoryId)
        );

    }

}

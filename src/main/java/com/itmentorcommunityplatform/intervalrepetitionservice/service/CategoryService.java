package com.itmentorcommunityplatform.intervalrepetitionservice.service;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.CategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.SavedSelectedCategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.CategoryWithSpecializationName;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.UserCategorySelection;
import com.itmentorcommunityplatform.intervalrepetitionservice.exception.ResourceAlreadyExistsException;
import com.itmentorcommunityplatform.intervalrepetitionservice.exception.ResourceNotFoundException;
import com.itmentorcommunityplatform.intervalrepetitionservice.mapper.CategoryMapper;
import com.itmentorcommunityplatform.intervalrepetitionservice.model.CategoryWithStatistics;
import com.itmentorcommunityplatform.intervalrepetitionservice.repository.CategoryRepository;
import com.itmentorcommunityplatform.intervalrepetitionservice.repository.UserCategorySelectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserCategorySelectionRepository selectionRepository;

    private final CategoryMapper categoryMapper;


    public List<CategoryResponseDto> getAllCategoriesBySpecialization(Long userId, Long specializationId) {

        List<CategoryWithStatistics> categories = categoryRepository.findCategoriesWithStatisticBySpecializationId(specializationId, userId);

        return categories.stream()
                .map(categoryMapper::toResponse)
                .toList();

    }

    @Transactional
    public List<SavedSelectedCategoryResponseDto> saveCategorySelection(Long userId, List<Long> selectedCategoryIds){

        List<UserCategorySelection> categorySelections = selectedCategoryIds.stream()
                .map(id -> new UserCategorySelection(id, userId))
                .toList();
        List<CategoryWithSpecializationName> categories = selectedCategoryIds.stream()
                .map(this::getCategoryWithSpecializationNameById)
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
                        category.getSpecializationName()
                ))
                .toList();
    }


    public CategoryWithSpecializationName getCategoryWithSpecializationNameById(Long categoryId) {

        return categoryRepository.
                findCategoryWithSpecializationNameById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category with id " + categoryId + " not found!"));

    }


}

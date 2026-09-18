package com.itmentorcommunityplatform.intervalrepetitionservice.service;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.CategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.CategoryWithQuestionsResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.SavedSelectedCategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.SelectedCategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.CategoryWithSpecializationName;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.UserCategorySelection;
import com.itmentorcommunityplatform.intervalrepetitionservice.exception.ResourceAlreadyExistsException;
import com.itmentorcommunityplatform.intervalrepetitionservice.exception.ResourceNotFoundException;
import com.itmentorcommunityplatform.intervalrepetitionservice.mapper.CategoryMapper;
import com.itmentorcommunityplatform.intervalrepetitionservice.mapper.SelectedCategoryMapper;
import com.itmentorcommunityplatform.intervalrepetitionservice.projection.CategoryWithStatistics;
import com.itmentorcommunityplatform.intervalrepetitionservice.repository.CategoryRepository;
import com.itmentorcommunityplatform.intervalrepetitionservice.repository.QuestionRepository;
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
    private final QuestionRepository questionRepository;

    private final CategoryMapper categoryMapper;
    private final SelectedCategoryMapper selectedCategoryMapper;


    public List<CategoryResponseDto> getAllCategoriesBySpecialization(Long userId, Long specializationId) {

        List<CategoryWithStatistics> categories = categoryRepository.findCategoriesWithStatisticBySpecializationId(specializationId, userId);

        return categoryMapper.toResponseList(categories);

    }

    public CategoryWithQuestionsResponseDto getCategoryWithQuestions(Long userId, Long categoryId) {

        CategoryWithStatistics category = categoryRepository.findCategoryWithStatisticsById(categoryId, userId).orElseThrow(() -> new ResourceNotFoundException("Category with id " + categoryId + " not found!"));

        return categoryMapper.toCategoryWithQuestionsResponse(category, questionRepository.findAllByCategoryId(categoryId));

    }

    public List<SelectedCategoryResponseDto> getSelectedCategories(Long userId) {

        List<CategoryWithStatistics> selectedCategories = categoryRepository.findAllSelectedCategoriesWithStatistic(userId);

        return selectedCategoryMapper.toSelectedCategoryResponseList(selectedCategories);
    }

    @Transactional
    public List<SavedSelectedCategoryResponseDto> saveCategorySelection(Long userId, List<Long> selectedCategoryIds){

        List<CategoryWithSpecializationName> categories = categoryRepository.findAllCategoriesWithSpecializationName();

        List<Long> categoriesIds = categories.stream()
                .map(CategoryWithSpecializationName::getId)
                .toList();

        for (Long selectedCategoryId : selectedCategoryIds) {
            if (!categoriesIds.contains(selectedCategoryId)) {
                throw new ResourceNotFoundException("Category with id " + selectedCategoryId + " not found!");
            }
        }

        List<CategoryWithSpecializationName> selectedCategories = categories.stream()
                .filter(category -> selectedCategoryIds.contains(category.getId()))
                .toList();

        List<UserCategorySelection> categorySelections = selectedCategoryIds.stream()
                .map(id -> new UserCategorySelection(id, userId))
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

        return selectedCategoryMapper.toSavedSelectedCategoryResponseList(selectedCategories);
    }

    public void deleteSelectedCategory(Long userId, Long categoryId) {

        checkIfCategoryExists(categoryId);

        selectionRepository.deleteByUserIdAndCategoryId(userId, categoryId);

    }

    public void checkIfCategoryExists(Long categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with id " + categoryId + " not found!"));
    }


}

package com.itmentorcommunityplatform.intervalrepetitionservice.service;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.CategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Category;
import com.itmentorcommunityplatform.intervalrepetitionservice.mapper.CategoryMapper;
import com.itmentorcommunityplatform.intervalrepetitionservice.model.UserCategoryStatistics;
import com.itmentorcommunityplatform.intervalrepetitionservice.repository.CategoryRepository;
import com.itmentorcommunityplatform.intervalrepetitionservice.repository.QuestionRepository;
import com.itmentorcommunityplatform.intervalrepetitionservice.repository.UserCategorySelectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final QuestionRepository questionRepository;
    private final CategoryRepository categoryRepository;
    private final UserCategorySelectionRepository selectionRepository;

    private final CategoryMapper categoryMapper;


    public List<CategoryResponseDto> getAllCategoriesBySpecialization(Long userId, Long specializationId) {

        List<Category> categories = (List<Category>) categoryRepository.findBySpecializationId(specializationId);

        Set<Long> selectedCategoryIds = selectionRepository.findCategoryIdsByUserId(userId);

        return categories.stream()
                .map(category -> categoryMapper.toResponse(
                        category,
                        selectedCategoryIds.contains(category.getId()),
                        getUserCategoryStatistics(userId, category.getId())
                ))
                .toList();

    }


    private UserCategoryStatistics getUserCategoryStatistics(Long userId, Long categoryId){

        return new UserCategoryStatistics(
                questionRepository.countNewByCategoryId(categoryId, userId),
                questionRepository.countReadyForRepetitionByCategoryId(categoryId, userId, Instant.now().toEpochMilli()),
                questionRepository.countAllByCategoryId(categoryId)
        );

    }

}

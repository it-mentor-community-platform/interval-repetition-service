package com.itmentorcommunityplatform.intervalrepetitionservice.mapper;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.CategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Category;
import com.itmentorcommunityplatform.intervalrepetitionservice.model.UserCategoryStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "newQuestions", source = "statistics.newQuestions")
    @Mapping(target = "questionsReadyToRepeat", source = "statistics.questionsReadyToRepeat")
    @Mapping(target = "allQuestions", source = "statistics.allQuestions")
    CategoryResponseDto toResponse(Category category, boolean selected, UserCategoryStatistics statistics);
}

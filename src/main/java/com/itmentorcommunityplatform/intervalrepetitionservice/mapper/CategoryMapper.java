package com.itmentorcommunityplatform.intervalrepetitionservice.mapper;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.CategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Category;
import com.itmentorcommunityplatform.intervalrepetitionservice.model.UserCategoryStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "new_questions", source = "statistics.newQuestions")
    @Mapping(target = "questions_ready_to_repeat", source = "statistics.questionsReadyToRepeat")
    @Mapping(target = "all_questions", source = "statistics.allQuestions")
    CategoryResponseDto toResponse(Category category, boolean selected, UserCategoryStatistics statistics);
}

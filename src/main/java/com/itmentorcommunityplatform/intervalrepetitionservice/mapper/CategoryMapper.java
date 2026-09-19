package com.itmentorcommunityplatform.intervalrepetitionservice.mapper;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.CategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.CategoryWithQuestionsResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.QuestionResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Question;
import com.itmentorcommunityplatform.intervalrepetitionservice.projection.CategoryWithStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = QuestionMapper.class)
public interface CategoryMapper {

    CategoryResponseDto toResponse(CategoryWithStatistics category);

    List<CategoryResponseDto> toResponseList(List<CategoryWithStatistics> categories);


    @Mapping(target = "specialization", source = "category.specializationName")
    CategoryWithQuestionsResponseDto toCategoryWithQuestionsResponse(CategoryWithStatistics category, List<Question> questions);

}

package com.itmentorcommunityplatform.intervalrepetitionservice.mapper;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.CategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.SavedSelectedCategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.CategoryWithSpecializationName;
import com.itmentorcommunityplatform.intervalrepetitionservice.model.CategoryWithStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponseDto toResponse(CategoryWithStatistics category);

    List<CategoryResponseDto> toResponseList(List<CategoryWithStatistics> categories);

    @Mapping(target = "id", source = "category.id")
    @Mapping(target = "name", source = "category.name")
    @Mapping(target = "specialization", source = "category.specializationName")
    SavedSelectedCategoryResponseDto toSelectedCategoryResponse(CategoryWithSpecializationName category);

    List<SavedSelectedCategoryResponseDto> toSelectedCategoryResponseList(
            List<CategoryWithSpecializationName> categories);
}

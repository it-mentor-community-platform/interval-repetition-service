package com.itmentorcommunityplatform.intervalrepetitionservice.mapper;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.CategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.SavedSelectedCategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.SelectedCategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.CategoryWithSpecializationName;
import com.itmentorcommunityplatform.intervalrepetitionservice.model.CategoryWithStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponseDto toResponse(CategoryWithStatistics category);

    List<CategoryResponseDto> toResponseList(List<CategoryWithStatistics> categories);

    @Mapping(target = "specialization", source = "category.specializationName")
    SavedSelectedCategoryResponseDto toSavedSelectedCategoryResponse(CategoryWithSpecializationName category);

    List<SavedSelectedCategoryResponseDto> toSavedSelectedCategoryResponseList(
            List<CategoryWithSpecializationName> categories);


    @Mapping(target = "specialization", source = "category.specializationName")
    SelectedCategoryResponseDto toSelectedCategoryResponse(CategoryWithStatistics category);


    List<SelectedCategoryResponseDto> toSelectedCategoryResponseList(
            List<CategoryWithStatistics> categories);

}

package com.itmentorcommunityplatform.intervalrepetitionservice.mapper;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.CategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.projection.CategoryWithStatistics;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponseDto toResponse(CategoryWithStatistics category);

    List<CategoryResponseDto> toResponseList(List<CategoryWithStatistics> categories);

}

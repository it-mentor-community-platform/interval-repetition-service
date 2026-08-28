package com.itmentorcommunityplatform.intervalrepetitionservice.mapper;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.CategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.SpecializationResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Specialization;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpecializationMapper {

    SpecializationResponseDto toResponse(
            Specialization specialization,
            List<CategoryResponseDto> categories
    );
}

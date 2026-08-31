package com.itmentorcommunityplatform.intervalrepetitionservice.dto;


import java.util.List;

public record SpecializationResponseDto(

        Long id,

        String name,

        List<CategoryResponseDto> categories

) {
}

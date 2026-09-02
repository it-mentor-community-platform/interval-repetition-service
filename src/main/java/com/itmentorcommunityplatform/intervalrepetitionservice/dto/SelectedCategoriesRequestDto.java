package com.itmentorcommunityplatform.intervalrepetitionservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SelectedCategoriesRequestDto(

        @NotEmpty(message = "Category IDs must not be empty")
        List<@NotNull(message = "Category ID must not be null") Long> categoryIds

) {
}

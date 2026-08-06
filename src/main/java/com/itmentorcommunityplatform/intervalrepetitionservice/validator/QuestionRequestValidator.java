package com.itmentorcommunityplatform.intervalrepetitionservice.validator;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.QuestionRequestDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.exception.ValidationException;

public class QuestionRequestValidator {

    private QuestionRequestValidator() {}

    public static void validate(QuestionRequestDto dto) {

        if (dto == null) {
            throw new ValidationException("Question request must not be null");
        }

        validateField(dto.specialization(), "Specialization");
        validateField(dto.category(), "Category");
        validateField(dto.title(), "Title");
        validateField(dto.answer(), "Answer");
    }


    private static void validateField(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new ValidationException(
                    fieldName + " must not be blank"
            );
        }
    }
}
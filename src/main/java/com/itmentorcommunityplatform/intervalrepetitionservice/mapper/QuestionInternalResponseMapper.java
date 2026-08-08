package com.itmentorcommunityplatform.intervalrepetitionservice.mapper;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.QuestionInsertInternalResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Category;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Question;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Specialization;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionInternalResponseMapper {

    QuestionInsertInternalResponseDto.Question map(Question question);

    QuestionInsertInternalResponseDto.Category map(Category category);

    QuestionInsertInternalResponseDto.Specialization map(Specialization specialization);

    default QuestionInsertInternalResponseDto map(
            Question question,
            Category category,
            Specialization specialization
    ) {
        return new QuestionInsertInternalResponseDto(
                map(question),
                map(category),
                map(specialization)
        );
    }
}

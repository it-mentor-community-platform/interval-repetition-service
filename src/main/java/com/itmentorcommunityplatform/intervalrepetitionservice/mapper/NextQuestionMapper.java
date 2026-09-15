package com.itmentorcommunityplatform.intervalrepetitionservice.mapper;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.NextQuestionResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.projection.NextQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NextQuestionMapper {

    @Mapping(target = "questionId", source = "id")
    NextQuestionResponseDto toResponse(NextQuestion nextQuestion);

}

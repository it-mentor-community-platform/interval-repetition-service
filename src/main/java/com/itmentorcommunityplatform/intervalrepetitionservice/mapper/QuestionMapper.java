package com.itmentorcommunityplatform.intervalrepetitionservice.mapper;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.QuestionResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {


    QuestionResponseDto toQuestionResponseDto(Question question);

}

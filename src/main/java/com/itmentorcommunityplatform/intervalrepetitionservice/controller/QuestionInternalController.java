package com.itmentorcommunityplatform.intervalrepetitionservice.controller;


import com.itmentorcommunityplatform.intervalrepetitionservice.docs.QuestionInternalDocs;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.QuestionInsertInternalResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.QuestionRequestDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/interval-repetition/internal/question")
public class QuestionInternalController {

    private final QuestionService questionService;

    @PostMapping
    @QuestionInternalDocs
    public ResponseEntity<QuestionInsertInternalResponseDto> save(@Valid @RequestBody QuestionRequestDto question) {

        QuestionInsertInternalResponseDto responseDto = questionService.save(question);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);

    }

}

package com.itmentorcommunityplatform.intervalrepetitionservice.controller;


import com.itmentorcommunityplatform.intervalrepetitionservice.docs.QuestionInternalDocs;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.QuestionRequestDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/interval-repetition/internal/question")
public class QuestionInternalController {

    private final QuestionService questionService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @QuestionInternalDocs
    public void save(@Valid @RequestBody QuestionRequestDto question) {

        questionService.save(question);

    }

}

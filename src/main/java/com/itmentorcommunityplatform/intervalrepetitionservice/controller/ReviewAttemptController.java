package com.itmentorcommunityplatform.intervalrepetitionservice.controller;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.ReviewAttemptDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.service.ReviewAttemptService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/interval-repetition/review-attempt")
public class ReviewAttemptController {

    private final ReviewAttemptService reviewAttemptService;

    @PostMapping
    public ResponseEntity<Void> reviewAttempt(@RequestHeader("X-Telegram-User-Id") Long userId, @Valid  @RequestBody ReviewAttemptDto reviewAttempt) {

        reviewAttemptService.reviewAttempt(reviewAttempt, userId);

        return ResponseEntity.ok().build();

    }


}

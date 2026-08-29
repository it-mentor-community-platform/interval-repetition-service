package com.itmentorcommunityplatform.intervalrepetitionservice.controller;

import com.itmentorcommunityplatform.intervalrepetitionservice.docs.GetSpecializationsDocs;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.SpecializationResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.service.SpecializationService;
import com.itmentorcommunityplatform.intervalrepetitionservice.validator.HeaderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/interval-repetition/specializations")
public class SpecializationController {

    private final SpecializationService specializationService;

    @GetMapping
    @GetSpecializationsDocs
    public ResponseEntity<List<SpecializationResponseDto>> getSpecializations(@RequestHeader(value = "X-Telegram-User-Id", required = false) Long userId) {

        HeaderValidator.validateIdHeader(userId);

        List<SpecializationResponseDto> specializations = specializationService.getAllSpecializations(userId);

        return ResponseEntity.ok(specializations);

    }


}

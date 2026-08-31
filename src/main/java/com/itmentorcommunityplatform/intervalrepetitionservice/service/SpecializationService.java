package com.itmentorcommunityplatform.intervalrepetitionservice.service;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.SpecializationResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Specialization;
import com.itmentorcommunityplatform.intervalrepetitionservice.mapper.SpecializationMapper;
import com.itmentorcommunityplatform.intervalrepetitionservice.repository.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecializationService {

    private final SpecializationRepository specializationRepository;

    private final CategoryService categoryService;

    private final SpecializationMapper specializationMapper;

    public List<SpecializationResponseDto> getAllSpecializations(Long userId) {

        List<Specialization> specializations = (List<Specialization>) specializationRepository.findAll();


        return specializations.stream()
                .map(specialization -> specializationMapper.toResponse(
                        specialization,
                        categoryService.getAllCategoriesBySpecialization(
                                userId,
                                specialization.getId()
                        )
                ))
                .toList();

    }


}

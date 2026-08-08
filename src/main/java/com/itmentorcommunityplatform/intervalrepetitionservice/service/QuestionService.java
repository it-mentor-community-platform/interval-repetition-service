package com.itmentorcommunityplatform.intervalrepetitionservice.service;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.QuestionInsertInternalResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.QuestionRequestDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Category;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Question;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Specialization;
import com.itmentorcommunityplatform.intervalrepetitionservice.mapper.QuestionInternalResponseMapper;
import com.itmentorcommunityplatform.intervalrepetitionservice.repository.CategoriesRepository;
import com.itmentorcommunityplatform.intervalrepetitionservice.repository.QuestionRepository;
import com.itmentorcommunityplatform.intervalrepetitionservice.repository.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final SpecializationRepository specializationRepository;
    private final CategoriesRepository categoriesRepository;
    private final QuestionRepository questionRepository;

    private final QuestionInternalResponseMapper mapper;

    @Transactional
    public QuestionInsertInternalResponseDto save(QuestionRequestDto question) {

        Specialization spec = specializationRepository
                .findByName(question.specialization())
                .orElseGet(() -> specializationRepository.save(
                        new Specialization(question.specialization()))
                );

        Category category = categoriesRepository
                .findByNameAndSpecializationId(question.category(),
                        spec.getId())
                .orElseGet(() -> categoriesRepository.save(
                        new Category(spec.getId(), question.category()))
                );

        Question finalQ = questionRepository
                .findByTitle(question.title())
                .map(q -> {
                    q.setAnswer(question.answer());
                    return q;
                })
                .orElseGet(() -> new Question(
                        category.getId(),
                        question.title(),
                        question.answer(),
                        true
                ));

        questionRepository.save(finalQ);

        return mapper.map(finalQ, category, spec);
    }


}

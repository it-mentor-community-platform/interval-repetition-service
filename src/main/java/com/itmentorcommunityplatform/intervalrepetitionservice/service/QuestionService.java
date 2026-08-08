package com.itmentorcommunityplatform.intervalrepetitionservice.service;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.QuestionRequestDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Category;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Question;
import com.itmentorcommunityplatform.intervalrepetitionservice.entity.Specialization;
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

    @Transactional
    public void save(QuestionRequestDto q) {

        Specialization spec = specializationRepository.findByName(q.specialization()).orElseGet(() -> specializationRepository.save(new Specialization(q.specialization())));

        Category category = categoriesRepository.findByNameAndSpecializationId(q.category(), spec.getId()).orElseGet(() -> categoriesRepository.save(new Category(spec.getId(), q.category())));

        Question finalQ = questionRepository.findByTitle(q.title()).map(question -> {
            question.setAnswer(q.answer());
            return question;
        }).orElseGet(() -> new Question(category.getId(), q.title(), q.answer(), true));

        questionRepository.save(finalQ);

    }


}

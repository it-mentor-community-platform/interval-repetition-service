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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final SpecializationRepository sRepository;
    private final CategoriesRepository cRepository;
    private final QuestionRepository qRepository;

    @Transactional
    public void save(QuestionRequestDto q) {

        Specialization spec = sRepository
                .findByName(q.specialization())
                .orElseGet(() -> sRepository.save(
                        new Specialization(q.specialization()))
                );

        Category category = cRepository
                .findByNameAndSpecializationId(q.category(), spec.getId())
                .orElseGet(() -> cRepository.save(
                        new Category(spec.getId(), q.category())))
                ;

        Question finalQ = qRepository
                .findByTitle(q.title())
                .map(question -> {
                    question.setAnswer(q.answer());
                    return question;
                })
                .orElseGet(() -> new Question(
                        category.getId(),
                        q.title(),
                        q.answer(),
                        true
                ));

        qRepository.save(finalQ);

    }


}

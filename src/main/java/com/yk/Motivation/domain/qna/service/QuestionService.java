package com.yk.Motivation.domain.qna.service;

import com.yk.Motivation.domain.qna.entity.Question;
import com.yk.Motivation.domain.qna.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getList() {
        return this.questionRepository.findAll();
    }

    public Question getQuestion(Integer id) {
        return this.questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question with id " + id + " not found"));
    }

    public Question create(Question question) {
        return this.questionRepository.save(question);
    }

    public void delete(Integer id) {
        questionRepository.deleteById(id);
    }
}

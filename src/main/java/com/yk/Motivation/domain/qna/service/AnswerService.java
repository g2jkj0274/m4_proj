package com.yk.Motivation.domain.qna.service;

import com.yk.Motivation.domain.qna.entity.Answer;
import com.yk.Motivation.domain.qna.entity.Question;
import com.yk.Motivation.domain.qna.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    @Transactional
    public void create(Question question, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(question);
        this.answerRepository.save(answer);
    }
}

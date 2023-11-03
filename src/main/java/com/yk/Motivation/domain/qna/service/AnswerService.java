package com.yk.Motivation.domain.qna.service;

import com.yk.Motivation.domain.qna.entity.Answer;
import com.yk.Motivation.domain.qna.entity.Question;
import com.yk.Motivation.domain.qna.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(Question question, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(question);
        this.answerRepository.save(answer);
    }
}

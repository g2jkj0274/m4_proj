package com.yk.Motivation.domain.qna.controller;

import com.yk.Motivation.domain.qna.entity.Question;
import com.yk.Motivation.domain.qna.service.AnswerService;
import com.yk.Motivation.domain.qna.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usr/qna/a")
@RequiredArgsConstructor
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam String content) {
        Question question = this.questionService.getQuestion(id);
        this.answerService.create(question, content);
        return String.format("redirect:/usr/qna/q/detail/%s", id);
    }

    // video in qna comment create
    @PostMapping("/videoInCreateAnswer/{id}")
    public String videoInCreateAnswer(Model model, @PathVariable("id") Integer id, @RequestParam String content) {
        Question question = this.questionService.getQuestion(id);
        this.answerService.create(question, content);
        return String.format("redirect:/usr/qna/q/videoInDetail/%s", id);
    }
}

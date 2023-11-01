package com.yk.Motivation.domain.qna.controller;

import com.yk.Motivation.domain.qna.entity.Question;
import com.yk.Motivation.domain.qna.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usr/qna/q")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "usr/qna/list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "usr/qna/detail";
    }

    @GetMapping("/create")
    public String questionCreate() {
        return "usr/qna/create";
    }

    @PostMapping("/create")
    public String create(
            @RequestParam("subject") String subject,
            @RequestParam("content") String content,
            @RequestParam("memberId") Long memberId) {

        // 질문을 생성하는 서비스 호출
        questionService.create(subject, content, memberId);

        // 생성 후 질문 목록 페이지로 리다이렉트
        return "redirect:/usr/qna/q/list";
    }
}

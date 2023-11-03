package com.yk.Motivation.domain.qna.controller;

import com.yk.Motivation.base.rq.Rq;
import com.yk.Motivation.domain.qna.entity.Question;
import com.yk.Motivation.domain.qna.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/usr/qna/q")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final Rq rq;

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

    // 질문 작성 페이지 뷰
    @GetMapping("/create")
    public String showCreate(Model model) {
        return "usr/qna/create"; // HTML 파일명을 여기에 맞춰주세요
    }

    // 질문 작성 데이터 처리
    @PostMapping("/create")
    public String handleCreate(Question question) {
        questionService.create(question);  // 데이터베이스에 저장하는 로직
        return "redirect:/usr/qna/q/list"; // 질문 목록 페이지로 리디렉션
    }
}

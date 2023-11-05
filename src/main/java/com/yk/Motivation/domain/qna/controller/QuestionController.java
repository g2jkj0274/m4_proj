package com.yk.Motivation.domain.qna.controller;

import com.yk.Motivation.base.rq.Rq;
import com.yk.Motivation.domain.lecture.service.LectureService;
import com.yk.Motivation.domain.lesson.service.LessonService;
import com.yk.Motivation.domain.member.entity.Member;
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
    private final LectureService lectureService;
    private final LessonService lessonService;

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
        return "usr/qna/create";
    }

    // 질문 작성 데이터 처리
    @PostMapping("/create")
    public String handleCreate(Question question) {
        questionService.create(question);  // 데이터베이스에 저장하는 로직
        return "redirect:/usr/qna/q/list"; // 질문 목록 페이지로 리디렉션
    }

    // 비디오 페이지 안의 QnA List
    @GetMapping("/videoInList")
    public String videoInList(Model model) {
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "usr/qna/videoInList";
    }

    @GetMapping(value = "/videoInDetail/{id}")
    public String videoInDetail(Model model, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "usr/qna/videoInDetail";
    }

    @GetMapping("/videoInCreate")
    public String videoInCreate(Model model) {
        return "usr/qna/videoInCreate";
    }
    /*
    @PostMapping("/videoInCreate")
    public String videoInHandleCreate(Question question) {
        questionService.create(question);  // 데이터베이스에 저장하는 로직
        return "redirect:/usr/qna/q/videoInList";
    }
    */
    @PostMapping("/videoInCreate")
    public String videoInHandleCreate(Question question) {
        Member loginedMember = rq.getMember();

        if(loginedMember == null) {
            return "redirect:/login";
        }

        question.setMember(loginedMember); // 로그인된 멤버 정보를 Question 객체에 설정
        questionService.create(question);  // 데이터베이스에 저장하는 로직
        return "redirect:/usr/qna/q/videoInList";
    }
}

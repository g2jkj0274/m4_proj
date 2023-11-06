package com.yk.Motivation.domain.qna.controller;

import com.yk.Motivation.base.rq.Rq;
import com.yk.Motivation.domain.lecture.entity.Lecture;
import com.yk.Motivation.domain.lesson.entity.Lesson;
import com.yk.Motivation.domain.lesson.service.LessonService;
import com.yk.Motivation.domain.member.entity.Member;
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
    private final Rq rq;
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
    @GetMapping("/videoInList/{lessonId}")
    public String videoInListWithLectureAndLesson(Model model, @PathVariable long lessonId) {
        // 레슨 ID를 사용하여 레슨 정보를 가져옵니다.
        Lesson lesson = lessonService.findById(lessonId).orElse(null);

        if (lesson != null) {
            // 레슨에서 강의 정보를 가져옵니다.
            Lecture lecture = lesson.getLecture();
            long lectureId = lecture.getId();

            // 모델에 강의 ID와 레슨 ID를 추가합니다.
            model.addAttribute("lectureId", lectureId);
            model.addAttribute("lessonId", lessonId);
        } else {
            // 적절한 오류 처리
            // 예를 들어, 에러 메시지를 추가하거나 오류 페이지로 리디렉션
        }

        // QnA 리스트를 가져옵니다.
        List<Question> questionList = questionService.getList();
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
    public String videoInCreate(
            @RequestParam(required = false) Long lectureId,
            @RequestParam(required = false) Long lessonId,
            Model model) {

        if (lectureId != null) model.addAttribute("lectureId", lectureId);
        if (lessonId != null) model.addAttribute("lessonId", lessonId);

        return "usr/qna/videoInCreate";
    }

    @PostMapping("/videoInCreate")
    public String videoInHandleCreate(Question question, @RequestParam("lesson") long lessonId) {
        Member loginedMember = rq.getMember();

        if(loginedMember == null) {
            return "redirect:/login";
        }

        question.setMember(loginedMember); // 로그인된 멤버 정보를 Question 객체에 설정
        questionService.create(question);  // 데이터베이스에 저장하는 로직

        // lessonId를 URL에 포함하여 리다이렉션
        return "redirect:/usr/qna/q/videoInList/" + lessonId;
    }
}

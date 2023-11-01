package com.yk.Motivation.domain.qna.service;

import com.yk.Motivation.domain.member.entity.Member;
import com.yk.Motivation.domain.member.repository.MemberRepository;
import com.yk.Motivation.domain.qna.entity.Question;
import com.yk.Motivation.domain.qna.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;

    public List<Question> getList() {
        return this.questionRepository.findAll();
    }

    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        question.isPresent();
        return question.get();
    }

    public void create(String subject, String content, Long memberId, int lectureId, int curriculumId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setLectureId(lectureId);
        q.setCurriculumId(curriculumId);
        q.setMember(member);

        this.questionRepository.save(q);
    }
}

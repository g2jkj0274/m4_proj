package com.yk.Motivation.domain.qna.service;

import com.yk.Motivation.base.rsData.RsData;
import com.yk.Motivation.domain.member.entity.Member;
import com.yk.Motivation.domain.qna.entity.Question;
import com.yk.Motivation.domain.qna.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> findLatestAfterId(int count, long lastId) {
        PageRequest pageRequest = PageRequest.of(0, count);

        if (lastId == 0) {
            // 만약 lastId가 0이면 최신 질문부터 시작
            return questionRepository.findByOrderByIdDesc(pageRequest);
        }

        // lastId 이후의 질문을 불러옴
        return questionRepository.findByIdLessThanOrderByIdDesc(lastId, pageRequest);
    }

    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    public List<Question> getList() {
        return this.questionRepository.findAll();
    }

    public Page<Question> getList(Pageable pageable) {
        return this.questionRepository.findAll(pageable);
    }

    public boolean hasAnswersByOthers(Question question, Member currentMember) {
        return question.getAnswerList().stream()
                .anyMatch(answer -> answer.getMember().getGrantedAuthorities()
                        .stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin")));
    }

    public Question getQuestion(Long id) {
        return this.questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question with id " + id + " not found"));
    }

    public Question create(Question question) {
        return this.questionRepository.save(question);
    }

    public void delete(Long id) {
        questionRepository.deleteById(id);
    }

    @Transactional
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public RsData<?> checkActorCanModify(Member member, Question question) {
        if (member == null || !member.equals(question.getMember())) {
            return new RsData<>("F-1", "권한이 없습니다.", null);
        }

        return new RsData<>("S-1", "가능합니다.", null);
    }

    public RsData<?> checkActorCanRemove(Member member, Question question) {
        return checkActorCanModify(member, question);
    }
}

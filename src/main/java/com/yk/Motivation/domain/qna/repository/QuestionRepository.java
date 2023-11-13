package com.yk.Motivation.domain.qna.repository;

import com.yk.Motivation.domain.qna.entity.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByOrderByIdDesc(Pageable pageable);
    List<Question> findByIdLessThanOrderByIdDesc(long lastId, Pageable pageable);
}

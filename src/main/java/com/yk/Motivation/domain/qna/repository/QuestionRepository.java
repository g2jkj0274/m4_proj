package com.yk.Motivation.domain.qna.repository;

import com.yk.Motivation.domain.qna.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}

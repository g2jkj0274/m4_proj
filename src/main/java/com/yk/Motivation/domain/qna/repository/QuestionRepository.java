package com.yk.Motivation.domain.qna.repository;

import com.yk.Motivation.domain.qna.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q WHERE q.lesson.id = :lessonId AND q.lecture.id = :lectureId ORDER BY q.id")
    Page<Question> findWithPriority(@Param("lectureId") long lectureId, @Param("lessonId") long lessonId, Pageable pageable);
}

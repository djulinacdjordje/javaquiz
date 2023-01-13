package com.example.project.repository;

import com.example.project.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("SELECT COUNT(DISTINCT question.id) FROM answer")
    Long countDistinctByQuestionId();

}

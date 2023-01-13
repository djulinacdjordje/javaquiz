package com.example.project.repository;

import com.example.project.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM question q ORDER BY RAND() LIMIT :numberOfRandomQuestions")
    List<Question> getRandomQuestion(Integer numberOfRandomQuestions);

}

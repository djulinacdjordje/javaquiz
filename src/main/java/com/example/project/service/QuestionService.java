package com.example.project.service;

import com.example.project.dto.QuestionAndAnswersDto;
import com.example.project.dto.QuestionDto;
import com.example.project.model.Question;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface QuestionService {

    List<Question> getAllQuestions();

    String checkAndAddNewQuestion(QuestionAndAnswersDto questionAndAnswers, BindingResult result, Model model);

    void deleteQuestion(Long id);

    List<Question> getAllQuestionsSortedByPercentageOfCorrectAnswer();

    List<QuestionDto> getAllSortedByMostChosenWrongAnswers();

}

package com.example.project.service;

import org.springframework.ui.Model;

import java.security.Principal;

public interface QuizService {

    String startQuiz(Model model);

    String updateDatabaseAfterAnswerAndContinueQuiz(Long id, Model model, String username);

}

package com.example.project.service;

import com.example.project.constant.Role;
import com.example.project.dto.QuizDto;
import com.example.project.model.Answer;
import com.example.project.model.AppUser;
import com.example.project.model.Question;
import com.example.project.repository.AnswerRepository;
import com.example.project.repository.AppUserRepository;
import com.example.project.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Optional;

import static com.example.project.constant.QuizConstants.NUMBER_OF_QUESTIONS;

@Service
public class QuizServiceImpl implements QuizService {

    private final AppUserRepository appUserRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private QuizDto quiz;

    @Autowired
    public QuizServiceImpl(AppUserRepository appUserRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.appUserRepository = appUserRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public String startQuiz(Model model) {
        if(questionRepository.findAll().size() >= NUMBER_OF_QUESTIONS){
            quiz = new QuizDto(questionRepository.getRandomQuestion(NUMBER_OF_QUESTIONS));
            model.addAttribute("quiz", quiz);
            return "quiz";
        }
        return "redirect:/quizmenu?error";
    }

    @Override
    public String updateDatabaseAfterAnswerAndContinueQuiz(Long id, Model model, Principal principal) {
        Optional<Answer> answers = answerRepository.findById(id);
        quiz.setCurrentQuestion(quiz.getCurrentQuestion() + 1);
        if(answers.isPresent()){
            Answer answer = answers.get();
            Question question = answer.getQuestion();

            answer.setNumberOfChoose(answer.getNumberOfChoose() + 1);
            if(answer.getCorrect()){
                question.setCorrectAnswers(question.getCorrectAnswers() + 1);
                quiz.setCorrectAnswers(quiz.getCorrectAnswers() + 1);
            }
            else{
                question.setWrongAnswers(question.getWrongAnswers() + 1);
            }
            quiz.setNumberOfAnswers(quiz.getNumberOfAnswers() + 1);

            answerRepository.save(answer);
            questionRepository.save(question);
        }
        else if(id == 0){
            quiz.setNumberOfAnswers(quiz.getNumberOfAnswers());
        }
        else{
            return "";
        }
        if(quiz.getCurrentQuestion() == quiz.getQuestions().size()){
            AppUser appUser = appUserRepository.findByUsername(principal.getName());
            if(quiz.getNumberOfAnswers() == quiz.getCurrentQuestion() && appUser.getAllAnswered() == null){
                appUser.setAllAnswered(true);
            }
            if(appUser.getHighScore() == null){
                appUser.setHighScore(0.0);
            }
            if((quiz.getCorrectAnswers().doubleValue() / quiz.getCurrentQuestion() * 100) > appUser.getHighScore()){
                appUser.setHighScore(quiz.getCorrectAnswers().doubleValue() / quiz.getCurrentQuestion() * 100);
            }
            appUserRepository.save(appUser);
            if(appUserRepository.findTop1ByRoleEqualsAndHighScoreIsNotNullOrderByHighScoreDesc(Role.CONTESTANT).equals(appUser)){
                return "redirect:/quizmenu/quiz/top5?congratulations";
            }

            return "redirect:/quizmenu/quiz/top5";
        }
        model.addAttribute("quiz", quiz);
        return "quiz";

    }

}

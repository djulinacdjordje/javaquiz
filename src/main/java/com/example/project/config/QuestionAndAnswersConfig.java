package com.example.project.config;

import com.example.project.model.Answer;
import com.example.project.model.Question;
import com.example.project.repository.AnswerRepository;
import com.example.project.repository.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class QuestionAndAnswersConfig {

    @Bean
    CommandLineRunner commandLineRunnerQuestionAndAnswers(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        return args -> {
            Question question = Question.builder()
                    .text("Koji auto vozi Brajan u prvoj trci u filmu 2 Fast 2 Furious?")
                    .correctAnswers(0)
                    .wrongAnswers(0)
                    .build();
            Set<Answer> answers = createAnswers();
            question.setAnswers(answers);
            questionRepository.save(question);
            for (Answer answer: answers
                 ) {
                answer.setQuestion(question);
                answerRepository.save(answer);
            }
            questionRepository.save(question);
        };
    }

    public Set<Answer> createAnswers() {
        Set<Answer> answers = new HashSet<>();
        answers.add(Answer.builder()
                .text("Honda S2000")
                .correct(false)
                .numberOfChoose(0)
                .build());
        answers.add(Answer.builder()
                .text("Nissan Skyline GT-R R34")
                .correct(true)
                .numberOfChoose(0)
                .build());
        answers.add(Answer.builder()
                .text("Toyota Supra Turbo MK IV")
                .correct(false)
                .numberOfChoose(0)
                .build());
        answers.add(Answer.builder()
                .text("Dodge Challenger R/T")
                .correct(false)
                .numberOfChoose(0)
                .build());
        return answers;
    }

}

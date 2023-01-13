package com.example.project.dto;

import com.example.project.model.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizDto {

    private Integer correctAnswers = 0;
    private Integer numberOfAnswers = 0;
    private Integer currentQuestion = 0;
    private List<Question> questions;

    public QuizDto(List<Question> questions){
        this.questions = questions;
    }

}

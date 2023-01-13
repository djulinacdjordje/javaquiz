package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAndAnswersDto {

    private String questionText;
    private Integer numberOfAnswers;
    private Integer correctAnswer;
    private List<String> answersText;

}

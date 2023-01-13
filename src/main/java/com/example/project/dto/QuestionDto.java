package com.example.project.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDto {

    private String questionText;
    private String answerText;
    private Double percentageOfMostWrongAnswer;

}

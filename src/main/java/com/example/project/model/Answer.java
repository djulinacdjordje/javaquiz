package com.example.project.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
@Entity(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "correct", nullable = false)
    private Boolean correct;

    @Column(name = "number_of_choose")
    private Integer numberOfChoose;

    @ManyToOne()
    private Question question;

}

package com.example.project.service;

import com.example.project.dto.QuestionAndAnswersDto;
import com.example.project.dto.QuestionDto;
import com.example.project.model.Answer;
import com.example.project.model.Question;
import com.example.project.repository.AnswerRepository;
import com.example.project.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.*;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public String checkAndAddNewQuestion(QuestionAndAnswersDto questionAndAnswers, BindingResult result, Model model) {
        if(questionAndAnswers.getQuestionText().isEmpty()){
            result.rejectValue("questionText", null, "Unesite tekst pitanja");
        }
        if(questionAndAnswers.getNumberOfAnswers() == null) {
            result.rejectValue("numberOfAnswers", null, "Izaberite broj odgovora");
        }
        else if(questionAndAnswers.getNumberOfAnswers() < 2 || questionAndAnswers.getNumberOfAnswers() > 5){
            result.rejectValue("numberOfAnswers", null, "Izaberite broj između 2 i 5");
        }
        if(questionAndAnswers.getAnswersText() != null){
            for (String answerText: questionAndAnswers.getAnswersText()
                 ) {
                if(answerText.equals("")){
                    result.rejectValue("numberOfAnswers", null, "Unesite tekst odgovora u sva polja");
                    break;
                }
            }
            if(questionAndAnswers.getCorrectAnswer() == null){
                result.rejectValue("numberOfAnswers", null, "Izaberite jedan tačan odgovor");
            }
        }
        else{
            if(questionAndAnswers.getNumberOfAnswers() != null){
                result.rejectValue("numberOfAnswers", null, "Greška pri dodavanju novog pitanja");
            }
        }

        if(result.hasErrors()){
            model.addAttribute("questionAndAnswers", questionAndAnswers);
            return "/addnewquestion";
        }
        else{
            return addNewUser(questionAndAnswers);
        }
    }

    private String addNewUser(QuestionAndAnswersDto questionAndAnswers){
        Question question = new Question(questionAndAnswers.getQuestionText());
        Set<Answer> answers = new HashSet<>();
        int counter = 1;

        for (String answerText: questionAndAnswers.getAnswersText()
        ) {
            Answer answer = new Answer();
            answer.setText(answerText);
            answer.setNumberOfChoose(0);
            answer.setQuestion(question);
            if(counter == questionAndAnswers.getCorrectAnswer()){
                answer.setCorrect(true);
            }
            else{
                answer.setCorrect(false);
            }

            answers.add(answer);
            counter++;
        }

        question.setAnswers(answers);

        questionRepository.save(question);

        return "redirect:/adminmenu/questions/add?success";
    }

    @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public List<Question> getAllQuestionsSortedByPercentageOfCorrectAnswer() {
        List<Question> questions = questionRepository.findAll();

        for (Question question : questions
        ) {
            question.setRatioCorrectWrong((question.getCorrectAnswers().doubleValue() / (question.getCorrectAnswers() + question.getWrongAnswers())) * 100.0);
        }

        Collections.sort(questions, Comparator.comparing(Question::getRatioCorrectWrong).reversed());

        return questions;
    }

    @Override
    public List<QuestionDto> getAllSortedByMostChosenWrongAnswers() {
        List<QuestionDto> questions = new ArrayList<>();
        List<Answer> answers = answerRepository.findAll();

        if(answers.size() != 0){
            var numberOfQuestions = answerRepository.countDistinctByQuestionId();
            var answerIdPercentage = mapOfAnswerIdAndPercentageOfMostWrongAnswer(numberOfQuestions, answers);

            for (Map.Entry<Long, Double> map : answerIdPercentage.entrySet()
            ) {
                Answer answer = answerRepository.findById(map.getKey()).get();
                QuestionDto question = QuestionDto.builder()
                        .answerText(answer.getText())
                        .questionText(answer.getQuestion().getText())
                        .percentageOfMostWrongAnswer(map.getValue())
                        .build();
                questions.add(question);
            }
        }

        return questions;
    }

    private HashMap<Long, Double> mapOfAnswerIdAndPercentageOfMostWrongAnswer(Long numberOfQuestions, List<Answer> answers) {
        HashMap<Long, Double> answerIdPercentage = new HashMap<>();

        int currently = 0;
        double percentageOfMostWrongAnswer;

        for(int i = 0; i < numberOfQuestions; i++){

            int sumNumberOfChoose = 0, maxNumberOfWrongChoose = -1;
            long answerId = -1;
            long questionId = answers.get(currently).getQuestion().getId();

            for(int j = currently; j < answers.size(); j++, currently++){
                if(answers.get(j).getQuestion().getId() == questionId){
                    sumNumberOfChoose += answers.get(j).getNumberOfChoose();
                    if(maxNumberOfWrongChoose < answers.get(j).getNumberOfChoose() && !answers.get(j).getCorrect()){
                        maxNumberOfWrongChoose = answers.get(j).getNumberOfChoose();
                        answerId = answers.get(j).getId();
                    }
                }
                else{
                    break;
                }
            }

            percentageOfMostWrongAnswer = (double)maxNumberOfWrongChoose / sumNumberOfChoose * 100;
            answerIdPercentage.put(answerId, percentageOfMostWrongAnswer);

        }

        return answerIdPercentage;
    }

}

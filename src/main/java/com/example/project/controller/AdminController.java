package com.example.project.controller;

import com.example.project.constant.Role;
import com.example.project.dto.QuestionAndAnswersDto;
import com.example.project.dto.QuestionDto;
import com.example.project.model.AppUser;
import com.example.project.model.Question;
import com.example.project.service.AppUserService;
import com.example.project.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(path = "adminmenu")
public class AdminController {

    private final AppUserService appUserService;
    private final QuestionService questionService;

    @Autowired
    public AdminController(AppUserService appUserService, QuestionService questionService) {
        this.appUserService = appUserService;
        this.questionService = questionService;
    }

    @GetMapping()
    public String menuPage(Principal principal) {
        if (appUserService.findUserByUsername(principal.getName()).getRole() == Role.ADMIN) {
            return "adminmenu";
        }
        return "";
    }

    @GetMapping(path = "/contestants/all")
    public String getAllContestants(Model model, Principal principal) {
        if (appUserService.findUserByUsername(principal.getName()).getRole() == Role.ADMIN) {
            List<AppUser> appUsers = appUserService.getAllContestants();
            model.addAttribute("appUsers", appUsers);
            return "contestants";
        }
        return "";
    }

    @GetMapping(path = "/contestants/allcorrect")
    public String getAllByHighScore(Model model, Principal principal) {
        if (appUserService.findUserByUsername(principal.getName()).getRole() == Role.ADMIN) {
            List<AppUser> appUsers = appUserService.getAllWithAllCorrect();
            model.addAttribute("appUsers", appUsers);
            return "contestantsallcorect";
        }
        return "";
    }

    @GetMapping(path = "/contestants/allanswered")
    public String getAllWithAllAnswered(Model model, Principal principal) {
        if (appUserService.findUserByUsername(principal.getName()).getRole() == Role.ADMIN) {
            List<AppUser> appUsers = appUserService.getAllWithAllAnswered();
            model.addAttribute("appUsers", appUsers);
            return "contestantsallanswered";
        }
        return "";
    }

    @GetMapping(path = "/contestants/sortedbyhighscore")
    public String getAllSortedByHighScore(Model model, Principal principal) {
        if (appUserService.findUserByUsername(principal.getName()).getRole() == Role.ADMIN) {
            List<AppUser> appUsers = appUserService.getAllSortedByHighScore();
            model.addAttribute("appUsers", appUsers);
            return "contestantssortedbyhighscore";
        }
        return "";
    }

    @GetMapping(path = "questions/add")
    public String showAddForm(Model model, Principal principal) {
        if (appUserService.findUserByUsername(principal.getName()).getRole() == Role.ADMIN) {
            QuestionAndAnswersDto questionAndAnswers = new QuestionAndAnswersDto();
            model.addAttribute("questionAndAnswers", questionAndAnswers);
            return "addnewquestion";
        }
        return "";
    }

    @PostMapping(path = "questions/add/save")
    public String addNewQuestionAndAnswers(
            @ModelAttribute("questionAndAnswers") QuestionAndAnswersDto questionAndAnswers, BindingResult result, Model model, Principal principal) {
        if (appUserService.findUserByUsername(principal.getName()).getRole() == Role.ADMIN) {
            return questionService.checkAndAddNewQuestion(questionAndAnswers, result, model);
        }
        return "";
    }

    @GetMapping(path = "/questions/delete")
    public String showDeleteForm(Model model, Principal principal) {
        if (appUserService.findUserByUsername(principal.getName()).getRole() == Role.ADMIN) {
            List<Question> questions = questionService.getAllQuestions();
            model.addAttribute("questions", questions);
            return "deletequestion";
        }
        return "";
    }

    @GetMapping(path = "/questions/delete/{id}")
    public String deleteQuestion(@PathVariable(value = "id") Long id, Principal principal) {
        if(appUserService.findUserByUsername(principal.getName()).getRole() == Role.ADMIN){
            questionService.deleteQuestion(id);
            return "redirect:/adminmenu/questions/delete?success";
        }
        return "";
    }

    @GetMapping(path = "/questions/sortedbypercentageofcorrectanswer")
    public String getAllSortedByCorrectAnswers(Model model, Principal principal) {
        if (appUserService.findUserByUsername(principal.getName()).getRole() == Role.ADMIN) {
            List<Question> questions = questionService.getAllQuestionsSortedByPercentageOfCorrectAnswer();
            model.addAttribute("questions", questions);
            return "questionssortedbypercentageofcorrectanswers";
        }
        return "";
    }

    @GetMapping(path = "/questions/sortedbymostchosenwronganswer")
    public String getAllSortedByMostChosenWrongAnswers(Model model, Principal principal) {
        if (appUserService.findUserByUsername(principal.getName()).getRole() == Role.ADMIN) {
            List<QuestionDto> questions = questionService.getAllSortedByMostChosenWrongAnswers();
            model.addAttribute("questions", questions);
            return "questionssortedbymostchosenwronganswer";
        }
        return "";
    }

}

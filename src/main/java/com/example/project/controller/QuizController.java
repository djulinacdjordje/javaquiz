package com.example.project.controller;

import com.example.project.constant.Role;
import com.example.project.model.AppUser;
import com.example.project.service.AppUserService;
import com.example.project.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(path = "quizmenu")
public class QuizController {

    private final AppUserService appUserService;
    private final QuizService quizService;

    @Autowired
    public QuizController(AppUserService appUserService, QuizService quizService) {
        this.appUserService = appUserService;
        this.quizService = quizService;
    }

    @GetMapping()
    public String menuPage(Principal principal) {
        if (appUserService.findUserByUsername(principal.getName()).getRole() == Role.CONTESTANT) {
            return "quizmenu";
        }
        return "";
    }

    @GetMapping(path = "/quiz/start")
    public String startQuiz(Model model, Principal principal) {
        if (appUserService.findUserByUsername(principal.getName()).getRole() == Role.CONTESTANT) {
            return quizService.startQuiz(model);
        }
        return "";
    }

    @GetMapping(path = "/quiz/addanswer/{id}")
    public String updateDatabaseAfterAnswerAndContinueQuiz(@PathVariable(value = "id") Long id, Model model, Principal principal) {
        if(appUserService.findUserByUsername(principal.getName()).getRole() == Role.CONTESTANT){
            return quizService.updateDatabaseAfterAnswerAndContinueQuiz(id, model, principal);
        }
        return "";
    }

    @GetMapping(path = "/quiz/top5")
    public String showHighScore(Model model, Principal principal){
        if(appUserService.findUserByUsername(principal.getName()).getRole() == Role.CONTESTANT){
            List<AppUser> appUsers = appUserService.getTop5SortedByHighScore();
            model.addAttribute("appUsers", appUsers);
            return "top5";
        }
        return "";
    }

}

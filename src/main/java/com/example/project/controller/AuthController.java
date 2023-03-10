package com.example.project.controller;

import com.example.project.constant.Role;
import com.example.project.model.AppUser;
import com.example.project.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class AuthController {

    private final AppUserService appUserService;

    @Autowired
    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping(path = "/index")
    public String homePage(){
        return "index";
    }

    @GetMapping(path = "/register")
    public String showRegistrationForm(Model model){
        AppUser appUser = new AppUser();
        model.addAttribute("appUser", appUser);
        return "register";
    }

    @PostMapping(path = "/register/save")
    public String registration(@ModelAttribute("appUser") AppUser appUser, BindingResult result, Model model){
        return appUserService.addNewUser(appUser, result, model);
    }

    @GetMapping(path = "login")
    public String showLoginForm(){
        return "login";
    }

    @GetMapping(path = "/loginsuccess")
    public String menuPage(Principal principal){
        AppUser appUser = appUserService.findUserByUsername(principal.getName());
        if(appUser != null){
            if(appUser.getRole() == Role.CONTESTANT){
                appUserService.updateTimestamp(appUser);
                return "quizmenu";
            }
            else if(appUser.getRole() == Role.ADMIN){
                return "adminmenu";
            }
        }
        return "";
    }

}

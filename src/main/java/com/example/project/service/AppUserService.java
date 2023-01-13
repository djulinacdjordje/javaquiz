package com.example.project.service;

import com.example.project.model.AppUser;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface AppUserService {

    String addNewUser(AppUser appUser, BindingResult result, Model model);

    void updateTimestamp(AppUser appUser);

    AppUser findUserByEmail(String email);

    AppUser findUserByUsername(String username);

    List<AppUser> getAllContestants();

    List<AppUser> getAllWithAllCorrect();

    List<AppUser> getAllWithAllAnswered();

    List<AppUser> getAllSortedByHighScore();

    List<AppUser> getTop5SortedByHighScore();

}

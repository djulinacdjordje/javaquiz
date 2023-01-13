package com.example.project.service;

import com.example.project.constant.Role;
import com.example.project.model.AppUser;
import com.example.project.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String addNewUser(AppUser appUser, BindingResult result, Model model) {

        if(appUser.getFirstName().isEmpty()){
            result.rejectValue("firstName", null, "Unesite ime");
        }
        if(appUser.getLastName().isEmpty()){
            result.rejectValue("lastName", null, "Unesite prezime");
        }
        if(appUser.getEmail().isEmpty()){
            result.rejectValue("email", null, "Unesite e-mail adresu");
        }
        if(appUser.getUsername().isEmpty()){
            result.rejectValue("username", null, "Unesite korisničko ime");
        }
        if(appUser.getPassword().isEmpty()){
            result.rejectValue("password", null, "Unesite lozinku");
        }

        AppUser existingUser = findUserByEmail(appUser.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null, "Nalog sa ovom e-mail adresom već postoji");
        }

        existingUser = findUserByUsername(appUser.getUsername());

        if(existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()){
            result.rejectValue("username", null, "Nalog sa ovim korisničkim imenom već postoji");
        }

        if(result.hasErrors()){
            model.addAttribute("appUser", appUser);
            return "/register";
        }

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setRole(Role.CONTESTANT);

        appUserRepository.save(appUser);

        return "redirect:/register?success";

    }

    @Override
    public void updateTimestamp(AppUser appUser) {
        appUser.setLastAccessedAt(Timestamp.from(Instant.now()));
        appUserRepository.save(appUser);
    }

    @Override
    public AppUser findUserByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    @Override
    public AppUser findUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> getAllContestants(){
        return appUserRepository.findAllByRoleEquals(Role.CONTESTANT);
    }

    @Override
    public List<AppUser> getAllWithAllCorrect() {
        return appUserRepository.findAllByHighScore(100.0);
    }

    @Override
    public List<AppUser> getAllWithAllAnswered(){
        return appUserRepository.findAllByAllAnswered(true);
    }

    @Override
    public List<AppUser> getAllSortedByHighScore() {
        return appUserRepository.findAllByRoleEqualsOrderByHighScoreDesc(Role.CONTESTANT);
    }

    @Override
    public List<AppUser> getTop5SortedByHighScore() {
        return appUserRepository.findTop5ByRoleEqualsAndHighScoreIsNotNullOrderByHighScoreDesc(Role.CONTESTANT);
    }

}

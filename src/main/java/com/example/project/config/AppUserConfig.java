package com.example.project.config;

import com.example.project.constant.Role;
import com.example.project.model.AppUser;
import com.example.project.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class AppUserConfig {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner commandLineRunnerAppUser(AppUserRepository repository){
        return args -> {
            AppUser admin = new AppUser(
                    "admin",
                    "admin",
                    "admin@gmail.com",
                    "admin",
                    passwordEncoder.encode("admin"),
                    Role.ADMIN
            );

            AppUser test = new AppUser(
                    "Test",
                    "User",
                    "testuser@gmail.com",
                    "test",
                    passwordEncoder.encode("test"),
                    Role.CONTESTANT
            );

            repository.saveAll(List.of(admin, test));
        };
    }

}

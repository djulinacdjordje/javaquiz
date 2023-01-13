package com.example.project.repository;

import com.example.project.constant.Role;
import com.example.project.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByEmail(String email);

    AppUser findByUsername(String username);

    List<AppUser> findAllByRoleEquals(Role role);

    List<AppUser> findAllByHighScore(Double highScore);

    List<AppUser> findAllByAllAnswered(Boolean allAnswered);

    List<AppUser> findAllByRoleEqualsOrderByHighScoreDesc(Role role);

    AppUser findTop1ByRoleEqualsAndHighScoreIsNotNullOrderByHighScoreDesc(Role role);

    List<AppUser> findTop5ByRoleEqualsAndHighScoreIsNotNullOrderByHighScoreDesc(Role role);

}

package org.ac.cst8277.ferguson.kyle.usermanagementservice.repository;

import org.ac.cst8277.ferguson.kyle.usermanagementservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndPassword(String username, String password);
    Optional<User> findByToken(String token);
    Optional<User> findByGithubId(String githubId);
}



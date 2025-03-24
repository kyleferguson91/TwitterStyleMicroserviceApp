package org.ac.cst8277.ferguson.kyle.usermanagementservice.service;

import lombok.RequiredArgsConstructor;
import org.ac.cst8277.ferguson.kyle.usermanagementservice.model.User;
import org.ac.cst8277.ferguson.kyle.usermanagementservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsernameAndPassword(username, password);
        userOpt.ifPresent(user -> {
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            userRepository.save(user);
        });
        return userOpt;
    }

    public Optional<User> getUserByToken(String token) {
        return userRepository.findByToken(token);
    }
}

package org.ac.cst8277.ferguson.kyle.usermanagementservice.controller;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.ac.cst8277.ferguson.kyle.usermanagementservice.model.Role;
import org.ac.cst8277.ferguson.kyle.usermanagementservice.model.User;
import org.ac.cst8277.ferguson.kyle.usermanagementservice.repository.RoleRepository;
import org.ac.cst8277.ferguson.kyle.usermanagementservice.repository.UserRepository;
import org.ac.cst8277.ferguson.kyle.usermanagementservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        Optional<User> userOpt = userService.authenticate(username, password);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
    
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/testRole/{id}")
public ResponseEntity<?> testRole(@PathVariable Long id) {
    Optional<Role> roleOpt = roleRepository.findById(id);
    if (roleOpt.isPresent()) {
        return ResponseEntity.ok(roleOpt.get());
    } else {
        return ResponseEntity.status(404).body("Role with ID " + id + " not found.");
    }
}


    @GetMapping("/roles")
    public ResponseEntity<?> getAllRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) {
        Set<Role> managedRoles = new HashSet<>();
        // For each role provided in the payload, fetch the managed role from the database.
        for (Role role : user.getRoles()) {
            Optional<Role> managedRole = roleRepository.findById(role.getId());
            if (managedRole.isPresent()) {
                managedRoles.add(managedRole.get());
            } else {
                // If the role doesn't exist, return a bad request response
                return ResponseEntity.badRequest()
                        .body("Role with ID " + role.getId() + " not found.");
            }
        }
        // Set the managed roles in the user
        user.setRoles(managedRoles);
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<?> getUserByToken(@PathVariable String token) {
        Optional<User> user = userService.getUserByToken(token);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }
}

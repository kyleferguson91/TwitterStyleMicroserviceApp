package org.ac.cst8277.ferguson.kyle.usermanagementservice.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ac.cst8277.ferguson.kyle.usermanagementservice.model.User;
import org.ac.cst8277.ferguson.kyle.usermanagementservice.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public OAuth2AuthenticationSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        
        // Extract GitHub user details
        String githubId = oauthToken.getPrincipal().getAttribute("id").toString();
        String username = oauthToken.getPrincipal().getAttribute("login");
        
        // Lookup user by GitHub ID; create if not exists
        Optional<User> userOpt = userRepository.findByGithubId(githubId);
        User user = userOpt.orElseGet(() -> {
            User newUser = new User();
            newUser.setGithubId(githubId);
            newUser.setUsername(username);
            // Optionally assign default roles here (e.g., SUBSCRIBER)
            return newUser;
        });

        // Generate a new UUID token
        String token = UUID.randomUUID().toString();
        user.setToken(token);

        // Optionally: set a token expiry (e.g., store current time + 15 minutes)
        
        // Save or update the user
        userRepository.save(user);

        // For demonstration: redirect to a page with the token in the query string
        // In a production app, you might store it in a secure cookie or session.
        response.sendRedirect("/?token=" + token);
    }
}

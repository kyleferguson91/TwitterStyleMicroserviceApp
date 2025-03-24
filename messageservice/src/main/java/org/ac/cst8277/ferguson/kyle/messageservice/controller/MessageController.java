package org.ac.cst8277.ferguson.kyle.messageservice.controller;

import lombok.RequiredArgsConstructor;
import org.ac.cst8277.ferguson.kyle.messageservice.model.Message;
import org.ac.cst8277.ferguson.kyle.messageservice.model.Subscription;
import org.ac.cst8277.ferguson.kyle.messageservice.service.MessageService;
import org.ac.cst8277.ferguson.kyle.messageservice.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final SubscriptionService subscriptionService;

    @Value("${usermanagement.url:http://localhost:8081}")
    private String userManagementUrl;

    private boolean isValidToken(String token) {
        try {
            String url = userManagementUrl + "/api/users/token/" + token;
            ResponseEntity<String> response = new RestTemplate().getForEntity(url, String.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            return false;
        }
    }

    private ResponseEntity<String> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
    }

    @PostMapping
    public ResponseEntity<?> postMessage(
            @RequestHeader("Authorization") String token,
            @RequestBody Message message) {

        if (!isValidToken(token)) return unauthorized();
        return ResponseEntity.ok(messageService.createMessage(message));
    }

    @GetMapping
    public ResponseEntity<?> getAllMessages(@RequestHeader("Authorization") String token) {
        if (!isValidToken(token)) return unauthorized();
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/producer/{producerId}")
    public ResponseEntity<?> getByProducer(
            @RequestHeader("Authorization") String token,
            @PathVariable String producerId) {

        if (!isValidToken(token)) return unauthorized();
        return ResponseEntity.ok(messageService.getMessagesByProducer(producerId));
    }

    @GetMapping("/subscriber/{subscriberId}")
    public ResponseEntity<?> getForSubscriber(
            @RequestHeader("Authorization") String token,
            @PathVariable String subscriberId) {

        if (!isValidToken(token)) return unauthorized();
        return ResponseEntity.ok(messageService.getMessagesForSubscriber(subscriberId));
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(
            @RequestHeader("Authorization") String token,
            @RequestBody Subscription subscription) {

        if (!isValidToken(token)) return unauthorized();
        return ResponseEntity.ok(subscriptionService.subscribe(subscription));
    }
}

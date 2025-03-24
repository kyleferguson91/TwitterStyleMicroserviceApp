package org.ac.cst8277.ferguson.kyle.messageservice.service;

import lombok.RequiredArgsConstructor;
import org.ac.cst8277.ferguson.kyle.messageservice.model.Subscription;
import org.ac.cst8277.ferguson.kyle.messageservice.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public Subscription subscribe(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }
}

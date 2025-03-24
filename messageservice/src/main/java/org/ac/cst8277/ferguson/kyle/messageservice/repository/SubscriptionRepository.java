package org.ac.cst8277.ferguson.kyle.messageservice.repository;

import org.ac.cst8277.ferguson.kyle.messageservice.model.Subscription;
import org.ac.cst8277.ferguson.kyle.messageservice.model.SubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionId> {
    List<Subscription> findBySubscriberId(String subscriberId);
}

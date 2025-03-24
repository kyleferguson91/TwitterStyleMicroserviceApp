package org.ac.cst8277.ferguson.kyle.messageservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "subscriptions")
@IdClass(SubscriptionId.class)
@Data
public class Subscription {

    @Id
    @Column(name = "subscriber_id")
    private String subscriberId;

    @Id
    @Column(name = "producer_id")
    private String producerId;
}

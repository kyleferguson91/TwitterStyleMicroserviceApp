package org.ac.cst8277.ferguson.kyle.messageservice.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class SubscriptionId implements Serializable {
    private String subscriberId;
    private String producerId;
}

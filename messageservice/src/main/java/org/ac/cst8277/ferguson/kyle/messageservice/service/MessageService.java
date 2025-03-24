package org.ac.cst8277.ferguson.kyle.messageservice.service;

import lombok.RequiredArgsConstructor;
import org.ac.cst8277.ferguson.kyle.messageservice.model.Message;
import org.ac.cst8277.ferguson.kyle.messageservice.repository.MessageRepository;
import org.ac.cst8277.ferguson.kyle.messageservice.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final SubscriptionRepository subscriptionRepository;

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Message> getMessagesByProducer(String producerId) {
        return messageRepository.findByProducerId(producerId);
    }

    public List<Message> getMessagesForSubscriber(String subscriberId) {
        var subscriptions = subscriptionRepository.findBySubscriberId(subscriberId);
        var producerIds = subscriptions.stream()
                .map(sub -> sub.getProducerId())
                .collect(Collectors.toList());

        return messageRepository.findAll().stream()
                .filter(msg -> producerIds.contains(msg.getProducerId()))
                .collect(Collectors.toList());
    }
}

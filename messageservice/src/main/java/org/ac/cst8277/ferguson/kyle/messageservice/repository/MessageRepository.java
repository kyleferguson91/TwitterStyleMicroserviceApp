package org.ac.cst8277.ferguson.kyle.messageservice.repository;

import org.ac.cst8277.ferguson.kyle.messageservice.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByProducerId(String producerId);
}

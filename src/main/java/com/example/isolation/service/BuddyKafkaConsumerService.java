package com.example.isolation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class BuddyKafkaConsumerService {
    private final BuddyService buddyService;

    @KafkaListener(topics = "${monitor.kafka.topic.name}", groupId = "${monitor.kafka.consumer.group-id}")
    public void listenBuddyLike(@Payload String message) {
        String[] nameAndLike = message.split(":");
        buddyService.addLikeToBuddyOverKafka(nameAndLike[0], Integer.parseInt(nameAndLike[1]));
    }
}

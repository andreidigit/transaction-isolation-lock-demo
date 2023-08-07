package com.example.isolation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class BuddyKafkaProducerService {
    @Value("${monitor.kafka.topic.name}")
    private String likesTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String name, int like) throws ExecutionException, InterruptedException {
        String nameAndLike = name + ":" + like;
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(likesTopic, nameAndLike);
        future.get();
    }


}

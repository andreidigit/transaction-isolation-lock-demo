package com.example.isolation.service;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LiveLagAnalyzerService {

    private final LagAnalyzerService lagAnalyzerService;
    private final String groupId;
    private final String topicName;
    private final AtomicInteger gaugeCounter;

    @Autowired
    public LiveLagAnalyzerService(
            LagAnalyzerService lagAnalyzerService,
            MeterRegistry registry,
            @Value(value = "${monitor.kafka.consumer.group-id}") String groupId,
            @Value(value = "${monitor.kafka.topic.name}") String topicName
    ) {
        this.lagAnalyzerService = lagAnalyzerService;
        this.groupId = groupId;
        this.topicName = topicName;
        this.gaugeCounter = new AtomicInteger(0);
        Gauge
                .builder("over.kafka.lag", gaugeCounter, AtomicInteger::get)
                .description("track lag of requests") // optional
                .tags("parser_name", "gauge_over_kafka")
                .tags("service", "monitoring")
                .register(registry);
    }

    @Scheduled(fixedDelay = 1000L)
    public void liveLagAnalysis() throws ExecutionException, InterruptedException {
        Map<TopicPartition, Long> lags = lagAnalyzerService.analyzeLag(groupId);

        for (Map.Entry<TopicPartition, Long> lagEntry : lags.entrySet()) {
            int partition = lagEntry.getKey().partition();
            String topic = lagEntry.getKey().topic();
            Long lag = lagEntry.getValue();

            if (topicName.equals(topic)) {
                gaugeCounter.set(Math.toIntExact(lag));
            }
        }
    }
}

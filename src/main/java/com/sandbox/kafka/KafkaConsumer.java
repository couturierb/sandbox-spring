package com.sandbox.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.sandbox.sse.SseEventDTO;

import static com.sandbox.kafka.KafkaProducer.TOPIC;

@Service
@Profile("kafka")
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
    private final ApplicationEventPublisher events;
    public static final String KAFKA_EVENT = "kafka event";

    public KafkaConsumer(ApplicationEventPublisher events) {
        this.events = events;
    }

    @KafkaListener(topics = TOPIC)
    public void listen(String message) {
        log.warn("Received message: " + message);
        events.publishEvent(new SseEventDTO(KAFKA_EVENT, message));
    }
}

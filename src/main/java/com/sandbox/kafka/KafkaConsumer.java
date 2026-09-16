package com.sandbox.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.sandbox.sse.SseService;

import static com.sandbox.kafka.KafkaProducer.TOPIC;

@Service
@Profile("kafka")
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
    private final SseService sseService;

    public KafkaConsumer(SseService sseService) {
        this.sseService = sseService;
    }

    @KafkaListener(topics = TOPIC)
    public void listen(String message) {
        log.warn("Received message: " + message);
        sseService.sendKafkaMessage(message);

    }
}

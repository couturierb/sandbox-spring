package com.sandbox.sse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.sandbox.sse.internal.SseService;

@RestController
@CrossOrigin(origins = "http://localhost:5173") // Adaptez selon le port de l'app front
public class SseController {

    private final SseService sseService;

    public SseController(SseService sseService) {
        this.sseService = sseService;
    }

    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamEvents() {
        return sseService.getStreamEvent();
    }

    @GetMapping(value = "/sse/kafka", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamEventsKafka() {
        return sseService.getKafkaStreamEvent();
    }
}

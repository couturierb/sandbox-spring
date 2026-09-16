package com.sandbox.sse;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class SseService {

    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final SseEmitter kafkaEmitter = new SseEmitter(0L);

    public SseEmitter getStreamEvent() {
        // Timeout de 30 secondes (0 ou -1 pour infini, mais déconseillé en prod)
        SseEmitter emitter = new SseEmitter(30_000L);

        executor.execute(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    Thread.sleep(2000);  // Simulation d'un traitement long

                    emitter.send(SseEmitter.event().data("Événement numéro " + i));
                }
                emitter.complete();
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    public SseEmitter getKafkaStreamEvent() {
        return kafkaEmitter;
    }

    public void sendKafkaMessage(String message) {
        executor.execute(() -> {
            try {
                kafkaEmitter.send(SseEmitter.event()
                                            .name("kafka")
                                            .data(message));
            } catch (IOException e) {
                kafkaEmitter.completeWithError(e);
            }
        });
    }
}

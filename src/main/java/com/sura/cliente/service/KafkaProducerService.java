package com.sura.cliente.service;

import com.sura.cliente.dto.ClienteEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topicName;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate,
                                @Value("${kafka.topics.client-events:client-events-topic}") String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
        log.info("KafkaProducerService inicializado con tópico: {}", topicName);
    }

    public void sendClientEvent(ClienteEventDto event) {
        try {
            log.debug("Enviando evento: {}", event.getEventType());

            kafkaTemplate.send(topicName, event.getNumeroDocumento(), event)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            log.info("Evento enviado exitosamente: {} para cliente: {}",
                                    event.getEventType(), event.getNumeroDocumento());
                            if (result != null) {
                                log.debug("Offset: {}, Partition: {}",
                                        result.getRecordMetadata().offset(),
                                        result.getRecordMetadata().partition());
                            }
                        } else {
                            log.error("Error enviando evento: {} para cliente: {}",
                                    event.getEventType(), event.getNumeroDocumento(), ex);
                        }
                    });

        } catch (Exception e) {
            log.error("Error enviando evento a Kafka: {}", e.getMessage(), e);
        }
    }
}

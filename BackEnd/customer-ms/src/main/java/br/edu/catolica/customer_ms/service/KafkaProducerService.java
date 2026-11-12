package br.edu.catolica.customer_ms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendEvent(String topicName, String key, Object event) {
        log.info("Enviando evento para o tópico: {}", topicName);
        log.info("Chave Kafka: '{}'", key);
        log.info("Conteúdo do evento: {}", event);



        kafkaTemplate.send(topicName, key, event)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error(
                                "Falha ao enviar evento para {}",
                                topicName,
                                exception
                        );
                    } else {
                        log.info(
                                "Evento enviado. Tópico={}, partição={}, offset={}",
                                result.getRecordMetadata().topic(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset()
                        );
                    }
                });
    }
}
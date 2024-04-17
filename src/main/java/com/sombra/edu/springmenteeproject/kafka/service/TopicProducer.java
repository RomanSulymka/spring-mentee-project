package com.sombra.edu.springmenteeproject.kafka.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicProducer {

    @Value("${topic.name.money}")
    private String topicMoney;

    @Value("${topic.name.wallet}")
    private String topicWallet;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMoney(String message) {
        log.info("Payload: {}", message);
        kafkaTemplate.send(topicMoney, message);
    }

    public void sendWallet(String message) {
        log.info("Payload {}", message);
        kafkaTemplate.send(topicWallet, message);
    }

}

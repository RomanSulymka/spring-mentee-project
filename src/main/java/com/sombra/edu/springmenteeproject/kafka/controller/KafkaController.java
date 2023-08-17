package com.sombra.edu.springmenteeproject.kafka.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sombra.edu.springmenteeproject.dto.SendMoneyDTO;
import com.sombra.edu.springmenteeproject.entity.Money;
import com.sombra.edu.springmenteeproject.kafka.service.TopicProducer;
import com.sombra.edu.springmenteeproject.service.MoneyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final TopicProducer topicProducer;
    private final MoneyService moneyService;
    private final ObjectMapper objectMapper;

    @GetMapping("/test")
    public void send() {
        topicProducer.sendMoney("Message send");
    }

    @GetMapping("/money")
    public ResponseEntity<String> sendAllMoneyData() {
        try {
            List<Money> monies = moneyService.getAllMoney();
            String moneyDataAsJson = objectMapper.writeValueAsString(monies);

            topicProducer.sendMoney(moneyDataAsJson);
            return new ResponseEntity<>(moneyDataAsJson, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/wallet")
    public ResponseEntity<String> sendMoneyInformation() {
        try {
            List<SendMoneyDTO> monies = moneyService.getMoneyInformation();
            String moneyDataAsJson = objectMapper.writeValueAsString(monies);

            topicProducer.sendWallet(moneyDataAsJson);
            return new ResponseEntity<>(moneyDataAsJson, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

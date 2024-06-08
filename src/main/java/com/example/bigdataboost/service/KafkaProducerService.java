package com.example.bigdataboost.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducerService {

    @Value("${topic.name}")
    private String topicName;

    //Kafka Template 을 이용해 Kafka Broker 전송
    private final KafkaTemplate<String,String> kafkaTemplate;

    public void sendMessageToKafka(String message, ZonedDateTime time) {
        log.info("Producer Message : {}, {}",message,time);
        kafkaTemplate.send(topicName,message);
    }
}

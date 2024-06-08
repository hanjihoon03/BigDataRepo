package com.example.bigdataboost.service;

import com.example.bigdataboost.etc.MessageQue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final MessageQue messageQue;

    @KafkaListener(topics = "test-batch-topic", groupId = "consumer_group_toy01")
    public void consume(String message) throws IOException {
        log.info("Consumed Message :{}", message);
        messageQue.addMessage(message); // 메시지를 큐에 저장
    }
}

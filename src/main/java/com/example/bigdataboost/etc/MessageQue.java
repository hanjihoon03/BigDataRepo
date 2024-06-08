package com.example.bigdataboost.etc;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class MessageQue {
    private final ConcurrentLinkedQueue<String> messageQueue = new ConcurrentLinkedQueue<>();

    public void addMessage(String message) {
        messageQueue.add(message);
    }

    public String getMessage() {
        return messageQueue.poll(); // 메시지를 큐에서 꺼내 반환, 큐가 비어있으면 null 반환
    }
}

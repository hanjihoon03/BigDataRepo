package com.example.bigdataboost.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {
    private final Environment env;


    public Map<String, Object> producerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                env.getProperty("spring.kafka.producer.bootstrap-servers"));

        //키와 값을 직렬화하는 클래스로 StringSerializer를 사용한다.
        //Kafka 프로듀서가 메시지를 브로커에 전송할 때 문자열로 직렬화하도록 설정한다.
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG
                , StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG
                , StringSerializer.class);
        return props;
    }


    //ProducerFactory 인터페이스의 구현체를 반환한다.
    //이 팩토리는 Kafka 프로듀서를 생성하는 데 사용된다.
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(this.producerConfig());
    }

    //KafkaTemplate은 메시지를 Kafka 토픽으로 전송하는 데 사용된다.
    //이 템플릿은 Kafka 프로듀서의 상위 수준 API로, 프로듀서의 여러 설정과 기능을 간단하게 사용할 수 있게 해준다.
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(this.producerFactory());
    }
}

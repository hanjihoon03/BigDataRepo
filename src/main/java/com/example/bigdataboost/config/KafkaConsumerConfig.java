package com.example.bigdataboost.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka //리스너를 사용하기 위한 필수적인 애노테이션
@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final Environment env;

    @Bean
    public Map<String, Object> consumerConfig() {
        // Kafka Consumer 설정을 담고 있는 맵을 생성한다.
        Map<String, Object> props = new HashMap<>();

        // Kafka 클러스터에 연결할 서버 정보다. 여러 서버를 ,로 구분하여 지정할 수 있다.
        // 예: "localhost:9092,localhost:9093"
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("spring.kafka.consumer.bootstrap-servers"));

        // Consumer 그룹 ID이다. 같은 그룹에 속한 Consumer들은 동일한 토픽의 메시지를 분산해서 처리한다.
        props.put(ConsumerConfig.GROUP_ID_CONFIG, env.getProperty("spring.kafka.consumer.group-id"));

        // Consumer가 읽기 시작할 오프셋을 지정한다. 옵션은 "earliest", "latest", "none" 중 하나다. yml에 earliest로 설정했다.
        // "earliest"는 가장 오래된 메시지부터 읽고, "latest"는 가장 최근 메시지부터 읽는다.
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, env.getProperty("spring.kafka.consumer.auto-offset-reset"));

        // 메시지 키의 역직렬화 클래스다. Kafka에서는 메시지를 전송할 때 바이트 배열로 전송하므로
        // 이를 다시 Java 객체로 변환하는 과정이 필요하다. 이 예제에서는 문자열로 역직렬한다.
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // 메시지 값의 역직렬화 클래스다. 키와 마찬가지로, 값을 바이트 배열에서 문자열로 변환한다.
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return props;
    }
    //DefaultKafkaConsumerFactory는 Consumer 인스턴스를 생성하는 팩토리다.
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,String> kafkaListenerContainerFactory() {
        // ConcurrentKafkaListenerContainerFactory는 Kafka 메시지 리스너 컨테이너를 관리하는 팩토리다.
        // 여러 개의 Kafka 리스너를 병렬로 실행할 수 있도록 설정할 수 있다.
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        // 리스너 컨테이너가 메시지를 소비할 수 있도록 Consumer 팩토리를 설정한다.
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}

package me.minseoky.kafka_poc.kafka_poc.simpleProduceAndConsume.service;

import lombok.RequiredArgsConstructor;
import me.minseoky.kafka_poc.avro.SimpleMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, SimpleMessage> kafkaTemplate;

    public void sendInTransaction(String topic, SimpleMessage message) {
        // transaction-id-prefix가 설정되어 있으면(네 yaml에 있음) 템플릿이 트랜잭션 지원 가능
        // 같은 트랜잭션 안에서 여러 send를 묶고 싶을 때 사용한다
        kafkaTemplate.executeInTransaction(kt -> {
            kt.send(topic, message.getKey(), message);
            // kt.send(topic, key, value + " (2nd)");
            return true;
        });
    }
}
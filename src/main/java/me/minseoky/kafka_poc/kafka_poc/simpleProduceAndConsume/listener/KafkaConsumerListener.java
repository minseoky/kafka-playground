package me.minseoky.kafka_poc.kafka_poc.simpleProduceAndConsume.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.minseoky.kafka_poc.avro.SimpleMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumerListener {

    // group-id, isolation.level(read_committed), ack-mode(manual)는 application.yaml 설정이 적용된다
    @KafkaListener(topics = "simple-produce-and-consume-avro")
    public void onMessage(
        ConsumerRecord<String, SimpleMessage> record,
        Acknowledgment ack,
        @Header(KafkaHeaders.GROUP_ID) String groupId
    ) {

        try {
            log.info(
                "groupId ({}), consumed topic={}, partition={}, offset={}, key={}, value={}",
                groupId,
                record.topic(),
                record.partition(),
                record.offset(),
                record.key(),
                record.value().toString()
            );
            // 수동 커밋(ack-mode=manual + enable-auto-commit=false)
            ack.acknowledge();
        } catch (Exception e) {
            // 여기서 ack를 안 하면 오프셋이 커밋되지 않아 재처리된다(=at-least-once)
            // 운영에서는 DLT(Dead Letter Topic)나 ErrorHandler 설정을 더하는 게 보통이다
            log.error("consume failed. will not ack. record={}", record, e);
            throw e; // 컨테이너 에러 핸들러가 받도록 던진다
        }
    }
}
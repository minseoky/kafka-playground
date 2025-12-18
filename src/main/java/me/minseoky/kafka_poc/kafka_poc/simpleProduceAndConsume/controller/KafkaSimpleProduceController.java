package me.minseoky.kafka_poc.kafka_poc.simpleProduceAndConsume.controller;

import lombok.RequiredArgsConstructor;
import me.minseoky.kafka_poc.kafka_poc.simpleProduceAndConsume.service.KafkaProducerService;
import me.minseoky.kafka_poc.avro.SimpleMessage;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kafka")
public class KafkaSimpleProduceController {

    private final KafkaProducerService producerService;

    @PostMapping("/send-tx")
    public String sendTx(
            @RequestParam(required = false, defaultValue = "") String key,
            @RequestParam String value
    ) {
        String topic = "simple-produce-and-consume-avro";
        SimpleMessage message = SimpleMessage.newBuilder()
                .setKey(key)
                .setValue(value)
                .build();
        producerService.sendInTransaction(topic, message);
        return "sent in tx";
    }
}
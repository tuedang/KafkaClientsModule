package com.fabfitfun.kafka.clients.consumer;

import com.fabfitfun.kafka.clients.annotation.Consumer;

public class KafkaConsumerHandler {
    @Consumer(topic = "kafka_topic_demo", groupId = "consumer_group_1")
    public void consumerOfMethod(KafkaMessage message) {
        System.out.println("consumerOfMethod-CALL HERE" + message);
    }

    @Consumer(topic = "kafka_topic_demo", groupId = "consumer_group_2")
    public void consumerSecond(String anotherMessage) {
        System.out.println("consumerSecond-CALL HERE" + anotherMessage);
    }
}

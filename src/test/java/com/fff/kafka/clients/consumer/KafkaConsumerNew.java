package com.fff.kafka.clients.consumer;

import com.fff.kafka.clients.annotation.Consumer;

public class KafkaConsumerNew {
    @Consumer(topic = "middleware_campaign_manager_test", groupId = "forlayo")
    public void consumerOfMethod(String message) {
        System.out.println("consumerOfMethod-CALL HERE" + message);
    }

    @Consumer(topic = "middleware_campaign_manager_test", groupId = "forlayo2")
    public void consumerSecond(String anotherMessage) {
        System.out.println("consumerSecond-CALL HERE" + anotherMessage);
    }
}

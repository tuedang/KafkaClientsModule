package com.fff.kafka.clients.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observer;
import rx.Subscription;

import java.util.Properties;

class ConsumerExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerExecutor.class);

    private String topic;
    private String groupId;
    private Observer<ConsumerRecord<String, ?>> observer;
    private Subscription subscription;
    private Deserializer<?> valueDeserializer;
    private KafkaConfig kafkaConfig;

    ConsumerExecutor(String topic, String groupId, Observer<ConsumerRecord<String, ?>> observer,
                     Deserializer<?> valueDeserializer, KafkaConfig kafkaConfig) {
        this.topic = topic;
        this.groupId = groupId;
        this.observer = observer;
        this.subscription = null;
        this.valueDeserializer = valueDeserializer;
        this.kafkaConfig = kafkaConfig;
    }

    void stop() {
        if (isRunning()) {
            subscription.unsubscribe();
        }
    }

    void start() {
        if (!isRunning()) {
            LOGGER.info("Create consumer for group={}, topic={}", groupId, topic);
            Properties consumerProperties = kafkaConfig.createConsumerConfigs(groupId);
            KafkaConsumer<String, ?> kafkaConsumer = new KafkaConsumer<>(consumerProperties, new StringDeserializer(), valueDeserializer);
            ObservableConsumer<?> rxConsumer = new ObservableConsumer<>(kafkaConsumer, topic);
            subscription = rxConsumer.toObservable().subscribe(observer);

        }
    }

    boolean isRunning() {
        return subscription != null && !subscription.isUnsubscribed();
    }

    public String getConsumerKey() {
        return String.format("%s:%s", topic, groupId);
    }
}

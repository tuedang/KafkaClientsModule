package com.fff.kafka.clients;

import com.fff.kafka.clients.consumer.ConsumerEngine;
import com.fff.kafka.clients.consumer.ConsumerProcessor;
import com.fff.kafka.clients.consumer.KafkaConfig;
import com.google.inject.AbstractModule;

public class KafkaClientsModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ConsumerProcessor.class).asEagerSingleton();
        bind(ConsumerEngine.class).asEagerSingleton();
        bind(KafkaConfig.class).asEagerSingleton();
    }

}

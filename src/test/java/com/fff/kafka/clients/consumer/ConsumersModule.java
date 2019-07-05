package com.fff.kafka.clients.consumer;

import com.google.inject.AbstractModule;

public class ConsumersModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(KafkaConsumerNew.class).asEagerSingleton();
    }

}

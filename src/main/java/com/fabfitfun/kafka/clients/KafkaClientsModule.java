package com.fabfitfun.kafka.clients;

import com.fabfitfun.kafka.clients.consumer.KafkaConfig;
import com.fabfitfun.kafka.clients.consumer.ConsumerEngine;
import com.fabfitfun.kafka.clients.consumer.ConsumerProcessor;
import com.google.inject.AbstractModule;

public class KafkaClientsModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ConsumerProcessor.class).asEagerSingleton();
        bind(ConsumerEngine.class).asEagerSingleton();
        bind(KafkaConfig.class).asEagerSingleton();
    }

}

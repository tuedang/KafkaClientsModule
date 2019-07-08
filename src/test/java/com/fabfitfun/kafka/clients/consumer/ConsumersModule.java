package com.fabfitfun.kafka.clients.consumer;

import com.google.inject.AbstractModule;
import org.apache.kafka.clients.producer.KafkaProducer;

public class ConsumersModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(KafkaConsumerNew.class).asEagerSingleton();
        bind(ObservableProducer.class).asEagerSingleton();
        bind(KafkaProducer.class).toProvider(ProducerProvider.class).asEagerSingleton();
    }

}

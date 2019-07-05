package com.fff.kafka.clients;

import com.fff.kafka.clients.consumer.ConsumerConnectorBuilder;
import com.fff.kafka.clients.consumer.ConsumerEngine;
import com.fff.kafka.clients.consumer.ConsumerProcessor;
import com.fff.kafka.clients.producer.ObservableProducer;
import com.fff.kafka.clients.provider.ProducerProvider;
import com.google.inject.AbstractModule;
import org.apache.kafka.clients.producer.KafkaProducer;

public class KafkaClientsModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(KafkaProducer.class).toProvider(ProducerProvider.class).asEagerSingleton();
        bind(ObservableProducer.class).asEagerSingleton();
        bind(ConsumerProcessor.class).asEagerSingleton();
        bind(ConsumerConnectorBuilder.class);
        bind(ConsumerEngine.class).asEagerSingleton();
    }

}

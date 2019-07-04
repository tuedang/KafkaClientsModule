package com.fff.kafka.clients.consumer;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.talanlabs.guicetools.scan.ComponentScanModule;
import rx.Subscriber;

import java.util.List;

public class ConsumersModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new ComponentScanModule("com.fff.kafka.clients.consumer", Singleton.class));
    }

    @Provides
    private List<Subscriber> provideConsumers(KafkaConsumer kafkaConsumer, KafkaConsumer2 kafkaConsumer2) {
        return Lists.newArrayList(kafkaConsumer, kafkaConsumer2);
    }
}

package com.fff.kafka.clients.consumer;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import rx.Subscriber;

import java.util.List;

public class ConsumersModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    private List<Subscriber> provideConsumers(KafkaConsumer circleBuilder,
                                              KafkaConsumer2 squareBuilder) {
        return Lists.newArrayList(circleBuilder, squareBuilder);
    }
}

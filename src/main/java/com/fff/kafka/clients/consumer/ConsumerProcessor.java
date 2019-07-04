package com.fff.kafka.clients.consumer;

import com.fff.kafka.clients.annotation.Consumer;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import rx.Observer;
import rx.Subscriber;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ConsumerProcessor {
    private Injector injector;
    private ConsumerConnectorBuilder consumerConnectorBuilder;

    @Inject(optional = true)
    private List<Subscriber> subscribers;

    @Inject
    public ConsumerProcessor(Injector injector, ConsumerConnectorBuilder consumerConnectorBuilder) {
        this.injector = injector;
        this.consumerConnectorBuilder = consumerConnectorBuilder;
    }

    public List<ConsumerExecutor> getConsumers() {

        if (subscribers == null) {
            return Collections.emptyList();
        }
        return subscribers.stream()
                .map(consumerClazz -> {
                    Consumer consumerAnnotation = consumerClazz.getClass().getAnnotation(Consumer.class);
                    Preconditions.checkState(consumerAnnotation != null, "Subscriber must be annotated by @Consumer");
                    
                    String topic = consumerAnnotation.topic();
                    String groupId = consumerAnnotation.groupId();
                    return new ConsumerExecutor(
                            topic,
                            groupId,
                            (Observer<ConsumerRecord>) consumerClazz,
                            consumerClazz.getClass(),
                            consumerConnectorBuilder
                    );
                })
                .collect(Collectors.toList());
    }
}

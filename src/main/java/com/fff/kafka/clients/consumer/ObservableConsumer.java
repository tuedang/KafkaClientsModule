package com.fff.kafka.clients.consumer;

import com.google.common.collect.ImmutableList;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ObservableConsumer {

    private KafkaConsumer<String, String> kafkaConsumer;
    private final String topic;

    public ObservableConsumer(KafkaConsumer<String, String> kafkaConsumer, String topic) {
        this.kafkaConsumer = kafkaConsumer;
        this.topic = topic;
    }

    public Observable<ConsumerRecord<String, String>> toObservable() {
        kafkaConsumer.subscribe(ImmutableList.of(topic));

        return Observable.interval(0, TimeUnit.MILLISECONDS)
                .map(aLong -> kafkaConsumer.poll(Duration.ofMillis(300)))
                .flatMap(stream -> Observable.from(stream).subscribeOn(Schedulers.io())
                ).asObservable();
    }


}

package com.fff.kafka.clients.consumer;

import com.google.common.collect.ImmutableList;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.time.Duration;

public class ObservableConsumer {

    private KafkaConsumer<String, String> kafkaConsumer;
    private final String topic;
    private int streams;

    public ObservableConsumer(KafkaConsumer<String, String> kafkaConsumer, String topic, int streams) {
        this.kafkaConsumer = kafkaConsumer;
        this.topic = topic;
        this.streams = streams;
    }

    public Observable<ConsumerRecord<String, String>> toObservable() {
        kafkaConsumer.subscribe(ImmutableList.of(topic));

        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(200));

        return Observable.from(records)
                .flatMap(consumerRecord -> Observable.just(consumerRecord)
                        .subscribeOn(Schedulers.io()
                        )).doOnUnsubscribe(() -> {
//                    kafkaConsumer.close();
                });
    }


}

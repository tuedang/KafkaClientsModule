package com.fff.kafka.clients.consumer;

import com.fff.kafka.clients.annotation.Consumer;
import com.google.inject.Inject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import rx.Subscriber;

@Consumer(topic = "middleware_campaign_manager_test", groupId = "forlayo", streams = 2)
public class KafkaConsumer extends Subscriber<ConsumerRecord<String, String>> {


  @Inject
  public KafkaConsumer() {

  }

  @Override
  public void onCompleted() {
//    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void onError(Throwable e) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void onNext(ConsumerRecord<String, String> consumerRecord) {
    System.out.println(consumerRecord.value() + "***** from KafkaConsumer1");
  }
}

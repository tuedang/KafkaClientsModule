package com.fff.kafka.clients.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.CLIENT_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

class ConsumerExecutor {
  private String topic;
  private String groupId;
  private Observer<ConsumerRecord> observer;
  private Subscription subscription;
  private ConsumerConnectorBuilder consumerConnectorBuilder;
  private Class consumerClass;
  private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerExecutor.class);

  ConsumerExecutor(String topic, String groupId, Observer<ConsumerRecord> observer, Class consumerClass, 
                   ConsumerConnectorBuilder consumerConnectorBuilder) {
    this.topic = topic;
    this.groupId = groupId;
    this.observer = observer;
    this.consumerConnectorBuilder = consumerConnectorBuilder;
    this.consumerClass = consumerClass;
    this.subscription = null;
  }

  void stop() {
    if (isRunning()) {
      subscription.unsubscribe();
    }
  }

  void start() {
    if (!isRunning()) {
      if(subscription != null && Subscriber.class.isAssignableFrom(consumerClass)) {
        throw new UnsupportedOperationException(
            String.format("Cannot resume the consumer %s as a Subscriber, don't inherit from Subscriber on Consumer if you want to use this feature, use Observer instead", consumerClass.getName())
        );
      }
      Properties consumerProperties = getDefaultConsumerConfigs();
      consumerProperties.put(GROUP_ID_CONFIG, groupId);
      KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer(consumerProperties, new StringDeserializer(), new StringDeserializer());
      ObservableConsumer rxConsumer = new ObservableConsumer(kafkaConsumer, topic);
      subscription = rxConsumer.toObservable().subscribe(observer);

    }
  }

  Class getConsumerClass() {
    return consumerClass;
  }


  boolean isRunning() {
    return subscription != null && !subscription.isUnsubscribed();
  }

  private Properties getDefaultConsumerConfigs() {
    Properties configProperties = new Properties();
    configProperties.put(CLIENT_ID_CONFIG, "client_id1");
    configProperties.put(GROUP_ID_CONFIG, "forlayo");
    configProperties.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    configProperties.put(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
    configProperties.put(VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
    configProperties.put(MAX_POLL_INTERVAL_MS_CONFIG, 1000);
    configProperties.put(SESSION_TIMEOUT_MS_CONFIG, 30000);
    configProperties.put("enable.auto.commit", true);
    configProperties.put("auto.commit.interval.ms", 12000);

    return configProperties;
  }
}

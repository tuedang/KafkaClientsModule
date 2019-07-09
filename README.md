# kafka-consumer module
Kafka consumer with Guice Module for handle kafka consumers and producers in a friendly way


# Getting Started

## Using the module in your server

### 1- Register the guice module in application bootstrap

```java
    KafkaClientsModule.class
```

### 3- Configuring Consumer inside KafkaConfig

You have all properties from kafka consumer and producer from the official docs availables in your archaius .properties files with a prefix for consumer and producer:
 
```
kafka.producer.bootstrap.servers=kafka.host:9092
kafka.producer.value.serializer=org.apache.kafka.common.serialization.StringSerializer
kafka.producer.key.serializer=org.apache.kafka.common.serialization.StringSerializer

kafka.consumer.zookeeper.connect=zookeper.host:2181
kafka.consumer.enable.auto.commit=true
kafka.consumer.auto.commit.interval.ms=10000
kafka.consumer.zookeeper.session.timeout.ms=500
kafka.consumer.zookeeper.sync.time.ms=250
kafka.consumer.partition.assignment.strategy=roundrobin

```
 
The group.id property from the consumer is set by the Consumer class with the Annotation @Consumer.
Also with @Consumer you can set the topic name


### 4- Building your own consumers
```java

public class KafkaConsumer {
    @Consumer(topic = "kafka_topic", groupId = "kafka_consumer_group")
    public void consumerWithObject(KafkaMessage message) {
        System.out.println("consumerOfMethod-CALL HERE" + message);
    }

    @Consumer(topic = "kafka_topic", groupId = "kafka_consumer_group_2")
    public void consumerWithString(String anotherMessage) {
        System.out.println("consumerSecond-CALL HERE" + anotherMessage);
    }
}

```
Note, the application is using deserialization with Gson on `JsonDeserializer` for general Object, and `JsonDeserializer` for text consumer



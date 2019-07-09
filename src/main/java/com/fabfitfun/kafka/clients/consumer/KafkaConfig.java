package com.fabfitfun.kafka.clients.consumer;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.CLIENT_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

public class KafkaConfig {
    private static final Logger log = LoggerFactory.getLogger(KafkaConfig.class);

    public interface KafkaConfigExtProvider {
        Map<String, ?> getConfiguration();
    }

    @Inject(optional = true)
    private KafkaConfigExtProvider kafkaConfigExtProvider;


    private Properties kafkaProperties;

    @PostConstruct
    public void initProperties() {
        Properties properties = new Properties();
        properties.putAll(createDefaultConsumerConfigs());
        if (kafkaConfigExtProvider != null) {
            log.info("KafkaConfigExtProvider is available, append to default kafka configuration");
            properties.putAll(kafkaConfigExtProvider.getConfiguration());
        }
        kafkaProperties = properties;
    }

    private Properties createDefaultConsumerConfigs() {
        Properties configProperties = new Properties();
        configProperties.put(CLIENT_ID_CONFIG, "client_id1");
        configProperties.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:90922");
        configProperties.put(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        configProperties.put(VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        configProperties.put(MAX_POLL_INTERVAL_MS_CONFIG, 1000);
        configProperties.put(SESSION_TIMEOUT_MS_CONFIG, 30000);
        configProperties.put(ENABLE_AUTO_COMMIT_CONFIG, true);
        configProperties.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, 12000);

        return configProperties;
    }

    public Properties getConsumerConfigs() {
        return (Properties) kafkaProperties.clone();
    }

    public Properties createConsumerConfigs(String consumerGroupId) {
        Properties configProperties = getConsumerConfigs();
        configProperties.put(GROUP_ID_CONFIG, consumerGroupId);
        return configProperties;
    }
}

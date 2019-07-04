package com.fff.kafka.clients.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;


public class ConsumerConnectorBuilder {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerConnectorBuilder.class);
  private Properties props;

  public ConsumerConnectorBuilder() {

    this.props = new Properties();

    // make kafka prop here

  }

  public ConsumerConnectorBuilder addGroupId(String groupId) {
    props.put("group.id", groupId);
    return this;
  }
  
}

package com.fabfitfun.kafka.clients.core;

import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.OutputStream;
import java.util.stream.Stream;
import org.codehaus.jackson.map.ObjectMapper;
import scmspain.karyon.restrouter.serializer.Serializer;

public class JsonSerializer extends Serializer {
  private ObjectMapper mapper = new ObjectMapper();

  public JsonSerializer() {
    super(Stream.of("application/json").toArray(String[]::new));
  }

  @Override
  public void serialize(Object obj, OutputStream outputStream) {
    Preconditions.checkNotNull(obj, "Object to serialize cannot be null");

    try {
      mapper.writeValue(outputStream, obj);
    }catch(IOException e) {
      throw new RuntimeException("Error serializing the handler return value: " + obj, e);
    }
  }

}

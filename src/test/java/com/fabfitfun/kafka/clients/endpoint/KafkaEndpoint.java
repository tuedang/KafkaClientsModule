package com.fabfitfun.kafka.clients.endpoint;

import com.fabfitfun.kafka.clients.consumer.ObservableProducer;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.inject.Inject;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import org.apache.kafka.clients.producer.ProducerRecord;
import rx.Observable;
import scmspain.karyon.restrouter.annotation.Endpoint;
import scmspain.karyon.restrouter.annotation.Path;

import javax.ws.rs.HttpMethod;
import java.util.concurrent.atomic.AtomicInteger;

@Endpoint
public class KafkaEndpoint {

    private ObservableProducer producer;
    private final int MESSAGES = 10;
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    @Inject
    public KafkaEndpoint(ObservableProducer producer) {
        this.producer = producer;
    }


    @Path(value = "/producer_test", method = HttpMethod.GET)
    public Observable<Void> postMessageToKafka(HttpServerRequest<ByteBuf> request,
                                               HttpServerResponse<ByteBuf> response) {
        String topic = "kafka_topic_demo";
        String value = "Lalalla";
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("message", new JsonPrimitive(value));

        for (int i = 0; i < MESSAGES; i++) {
            jsonObject.add("id", new JsonPrimitive(ATOMIC_INTEGER.incrementAndGet()));
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, jsonObject.toString());
            producer.send(producerRecord)
                    .doOnNext(recordMetadata -> {
                        // log action
                    })
                    .subscribe();

        }
        return response.writeStringAndFlush("success_message");
    }

}


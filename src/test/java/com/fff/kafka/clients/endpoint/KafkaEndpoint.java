package com.fff.kafka.clients.endpoint;

import com.fff.kafka.clients.producer.ObservableProducer;
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
        String topic = "middleware_campaign_manager_test";
        String value = "Lalalla";
        String key = "42";

        ProducerRecord<String, String> producerRecord;

        for (int i = 0; i < MESSAGES; i++) {
            producerRecord = new ProducerRecord<>(topic, value + "_" + ATOMIC_INTEGER.incrementAndGet());
            producer.send(producerRecord)
                    .doOnNext(recordMetadata -> {
//                  System.out.println((String.format("Offset: %d partition: %d", recordMetadata.offset(), recordMetadata.partition())));
                    })
                    .subscribe();

        }
        return response.writeStringAndFlush("forlayo");
    }

}


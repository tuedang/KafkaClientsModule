package com.fff.kafka.clients;

import com.fff.kafka.clients.core.KaryonControllerTest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

public class ProducerEndpointTest extends KaryonControllerTest {
    @Test
    public void itShouldProduceALotOfMessages() throws Exception {
        int i = 3;
        while (--i > 0) {
            createData();
            Thread.sleep(1000);
            System.out.println("NEXT-" + i);
        }

    }

    public void createData() {
        final String body = createHttpClient()
                .submit(
                        HttpClientRequest.createGet("/producer_test")
                )
                .doOnNext(response -> Assert.assertEquals(HttpResponseStatus.OK, response.getStatus()))
                .flatMap(HttpClientResponse::getContent)
                .map(this::asString)
                .timeout(500, TimeUnit.SECONDS)
                .toBlocking().single();

        Assert.assertEquals("forlayo", body);
    }


}

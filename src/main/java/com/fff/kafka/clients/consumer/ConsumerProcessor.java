package com.fff.kafka.clients.consumer;

import com.fff.kafka.clients.annotation.Consumer;
import com.fff.kafka.clients.core.GuiceUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import rx.Observer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ConsumerProcessor {
    private Injector injector;
    private ConsumerConnectorBuilder consumerConnectorBuilder;

    @Inject
    public ConsumerProcessor(Injector injector, ConsumerConnectorBuilder consumerConnectorBuilder) {
        this.injector = injector;
        this.consumerConnectorBuilder = consumerConnectorBuilder;
    }

    public List<ConsumerExecutor> getConsumers() {
        Set<Method> consumerMethods = GuiceUtils.findConsumerInjector(injector);
        if (consumerMethods.isEmpty()) {
            return Collections.emptyList();
        }

        List<ConsumerExecutor> executors = Lists.newArrayList();
        for (Method consumerMethod : consumerMethods) {
            Preconditions.checkArgument(consumerMethod.getParameterCount() == 1, "Consumer function must contain only one parameter");

            Class parameterType = consumerMethod.getParameters()[0].getType();
            Object consumerObject = injector.getInstance(consumerMethod.getDeclaringClass());
            Consumer consumer = consumerMethod.getAnnotation(Consumer.class);
            Observer<ConsumerRecord<String, ?>> observer = new ConsumerObserverAdapter(consumerObject, consumerMethod);

            //converter consumerRecord to appropriate object type on parameterType
            executors.add(new ConsumerExecutor(consumer.topic(), consumer.groupId(), observer, parameterType, null));
        }
        return executors;
    }

    class ConsumerObserverAdapter implements Observer<ConsumerRecord<String, ?>> {
        private final Object consumerObject;
        private final Method methodConsumer;

        public ConsumerObserverAdapter(Object target, Method methodConsumer) {
            this.consumerObject = target;
            this.methodConsumer = methodConsumer;
        }

        @Override
        public void onCompleted() {
            System.out.println("onComplete-commit?");
        }

        @Override
        public void onError(Throwable throwable) {
            throw new RuntimeException("Cannot handle the ConsumerRecord", throwable);
        }

        @Override
        public void onNext(ConsumerRecord consumerRecord) {
            try {
                methodConsumer.invoke(consumerObject, consumerRecord.value());
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Cannot invoke method", e);
            }
        }
    }
}

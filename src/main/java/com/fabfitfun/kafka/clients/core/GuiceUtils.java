package com.fabfitfun.kafka.clients.core;

import com.fabfitfun.kafka.clients.annotation.Consumer;
import com.google.inject.Injector;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;

public class GuiceUtils {
    public static Set<Method> findConsumerInjector(Injector injector) {
        Set<Class<?>> allInjectionClasses = injector.getAllBindings().keySet().stream()
                .map(binding -> binding.getTypeLiteral().getRawType())
                .collect(Collectors.toSet());

        Reflections reflections = new Reflections(allInjectionClasses, new MethodAnnotationsScanner());
        return reflections.getMethodsAnnotatedWith(Consumer.class);
    }
}

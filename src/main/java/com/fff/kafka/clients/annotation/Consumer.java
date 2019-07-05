package com.fff.kafka.clients.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

@Target({METHOD, CONSTRUCTOR, FIELD, TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Consumer {
  String topic();
  String groupId();
}

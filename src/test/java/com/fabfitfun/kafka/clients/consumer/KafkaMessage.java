package com.fabfitfun.kafka.clients.consumer;

public class KafkaMessage {
    private Integer id;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "KafkaMessage{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}

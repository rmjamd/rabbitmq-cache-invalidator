package com.ramij.CacheInvalidator.rmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MessageTopicImpl<EntityType> implements MessageTopic<EntityType> {
    private final Channel channel;
    private final String id;
    private final String queueName;
    private final String MESSAGE_EXCHANGE = "message_exchange";
    private final Class<EntityType> type;
    private MessageListener<EntityType> listener;


    public MessageTopicImpl(Channel channel, String id, String queueName, Class<EntityType> type) throws IOException {
        this.channel = channel;
        this.id = id;
        this.queueName = queueName;
        this.type = type;
        channel.exchangeDeclare(MESSAGE_EXCHANGE, "topic");

        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, MESSAGE_EXCHANGE, queueName + MESSAGE_EXCHANGE);
        channel.basicConsume(queueName, (consumerTag, delivery) -> {
            Map<String, Object> headers = delivery.getProperties().getHeaders();
            if (headers.get("Id") != id) {
                listener.onMessage(new ObjectMapper().readValue(delivery.getBody(), type));
            }
        }, (x) -> {
        });

    }

    @Override
    public void addListener(MessageListener listener) {
        this.listener = listener;
    }

    @Override
    public void removeListener() {
        this.listener = null;
    }

    @Override
    public void publish(EntityType t) {
        try {
            byte[] bytes = new ObjectMapper().writeValueAsString(t).getBytes(StandardCharsets.UTF_8);
            Map<String, Object> map = new HashMap<>();
            map.put("Id", id);
            AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().headers(map).build();
            channel.basicPublish(queueName, queueName + MESSAGE_EXCHANGE, properties, bytes);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}

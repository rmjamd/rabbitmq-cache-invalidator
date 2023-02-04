package com.ramij.CacheInvalidator.rmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.ramij.CacheInvalidator.ObjectMapperUtil;

import java.util.HashMap;
import java.util.Map;

public class MessageTopicImpl<EntityType> implements MessageTopic<EntityType> {
    private final Channel channel;
    private final String id;
    private final String topicName;
    private final String MESSAGE_EXCHANGE = "message_exchange";
    private final Class<EntityType> type;
    private MessageListener<EntityType> listener;
    private final ObjectMapperUtil<EntityType> mapperUtil = new ObjectMapperUtil<>();


    public MessageTopicImpl(Channel channel, String id, String topicName, Class<EntityType> type) {
        this.channel = channel;
        this.id = id;
        this.topicName = topicName;
        this.type = type;
        initializeQueue();

    }

    private void initializeQueue() {
        try {
            String queueName = id + ":" + topicName;
            channel.exchangeDeclare(MESSAGE_EXCHANGE, "topic");
            channel.queueDeclare(queueName, true, true, false, new HashMap<>());
            channel.queueBind(queueName, MESSAGE_EXCHANGE, topicName);
            channel.basicQos(500, true);
            channel.basicConsume(queueName, false, (consumerTag, delivery) -> {
                Map<String, Object> headers = delivery.getProperties().getHeaders();
                if (!headers.get("Id").toString().equals(id)) {
                    EntityType entityType = mapperUtil.deserialize(delivery.getBody(), type);
                    listener.onMessage(entityType);
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                } else {
                    channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
                }
            }, (consumerTag) -> {
            });
        } catch (Exception e) {
            throw new RuntimeException();
        }

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
            byte[] bytes = mapperUtil.serialize(t);
            Map<String, Object> map = new HashMap<>();
            map.put("Id", id);
            AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().headers(map).build();
            channel.basicPublish(MESSAGE_EXCHANGE, topicName, properties, bytes);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}

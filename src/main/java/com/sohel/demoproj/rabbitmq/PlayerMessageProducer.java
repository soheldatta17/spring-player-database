package com.sohel.demoproj.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.sohel.demoproj.config.RabbitMQConfig;
import com.sohel.demoproj.event.PlayerCreatedEvent;

@Service
public class PlayerMessageProducer {

    private static final Logger log = LoggerFactory.getLogger(PlayerMessageProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public PlayerMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendPlayerCreatedEvent(PlayerCreatedEvent event) {
        log.info("Producer: Publishing PlayerCreatedEvent -> {}", event);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, event);
    }
}

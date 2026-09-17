package com.sohel.demoproj.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.sohel.demoproj.config.RabbitMQConfig;
import com.sohel.demoproj.event.PlayerCreatedEvent;

@Service
public class PlayerMessageConsumer {

    private static final Logger log = LoggerFactory.getLogger(PlayerMessageConsumer.class);

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consumePlayerCreatedEvent(PlayerCreatedEvent event) {
        log.info("Consumer: Received PlayerCreatedEvent -> {}", event);
        // Here you can process the event, e.g., send a welcome email, etc.
    }
}

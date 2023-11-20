package com.assignment.coda.registryservice.registry.service;

import com.assignment.coda.registryservice.registry.dto.RegistryEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * A service class providing an implementation to {@link RabbitService}
 * to handle RabbitMQ logic
 */
@Service
public class RabbitServiceImpl implements RabbitService {

    private final Logger logger = LoggerFactory.getLogger(RabbitServiceImpl.class);

    private RabbitTemplate rabbitTemplate;


    @Value("${rabbitmq.routing.key}")
    private String registryKey;

    @Autowired
    public RabbitServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Publish {@link RegistryEvent} over RabbitMQ.
     * If an {@link AmqpException} is thrown, the method with retry with the same argument.
     * If the maxAttempts is reached, the recovery method {@link #sentRecover(AmqpException, RegistryEvent) sentRecover} will be executed.
     * @param event a {@link RegistryEvent} represent a change made on the registry list
     * @throws AmqpException when there is any error with the queue
     */
    @Override
    @Retryable(retryFor = AmqpException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public void sent(final RegistryEvent event) throws AmqpException {
        logger.info("Sent registry event: {}", event);
        rabbitTemplate.convertAndSend(registryKey, event);
    }

    /**
     * Recovery method for {@link #sent(RegistryEvent) sent}
     * @param e     an exception {@link AmqpException} of the last try
     * @param event a {@link RegistryEvent} represent a change made on the registry list
     */
    @Override
    public void sentRecover(AmqpException e, RegistryEvent event) {
        logger.error("Reach max retry for sending registry event to queue: {}", event);
    }
}

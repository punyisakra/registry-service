package com.assignment.coda.registryservice.registry.service;

import com.assignment.coda.registryservice.registry.dto.RegistryEvent;
import org.springframework.amqp.AmqpException;

/**
 * A service interface implemented by {@link RabbitServiceImpl}
 * to handle RabbitMQ logic
 */
public interface RabbitService {
    /**
     * Publish {@link RegistryEvent} over RabbitMQ.
     * If an {@link AmqpException} is thrown, the method with retry with the same argument.
     * If the maxAttempts is reached, the recovery method {@link #sentRecover(AmqpException, RegistryEvent) sentRecover} will be executed.
     * @param event a {@link RegistryEvent} represent a change made on the registry list
     * @throws AmqpException when there is any error with the queue
     */
    void sent(RegistryEvent event) throws AmqpException ;

    /**
     * Recovery method for {@link #sent(RegistryEvent) sent}
     * @param e     an exception {@link AmqpException} of the last try
     * @param event a {@link RegistryEvent} represent a change made on the registry list
     */
    void sentRecover(AmqpException e, RegistryEvent event);
}

package com.assignment.coda.registryservice.registry.service;

import com.assignment.coda.registryservice.registry.dto.RegistryEvent;
import org.springframework.amqp.AmqpException;

public interface RabbitService {
    void sent(RegistryEvent event);

    void sentRecover(AmqpException e, RegistryEvent event);
}

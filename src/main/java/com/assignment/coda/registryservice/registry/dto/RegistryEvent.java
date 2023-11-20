package com.assignment.coda.registryservice.registry.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * Represent an event when the registry list is changed, used for
 * sharing the change with routing-service via RabbitMQ
 * */
@Component
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class RegistryEvent {
    /**
     * Action string such as "add" or "remove"
     */
    private String action;

    /**
     * {@link Instance} that was changed depending on the action property
     */
    private Instance instance;
}

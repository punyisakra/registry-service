package com.assignment.coda.registryservice.registry.service;

import com.assignment.coda.registryservice.registry.dto.Instance;
import com.assignment.coda.registryservice.registry.dto.RegistryEvent;

import java.util.List;

public interface RegistryService {
    /**
     * Register the input instance by adding the instance to the registry list.
     * If success, publish the {@link RegistryEvent} to the RabbitMQ.
     * @param instance an application instance of type {@link Instance} to be registered
     * @return true if register process is success; else false
     */
    boolean register(Instance instance);

    /**
     * Get all the instances that have been registered with the registry.
     * Will return empty {@link List} if no instance has registered.
     * @return a list of type {@link List<Instance>} represented all {@link Instance}s which were
     * registered with the registry
     */
    List<Instance> getRegistryList();

    /**
     * Remove the input instance from the registry list.
     * If success, publish the {@link RegistryEvent} to the RabbitMQ.
     * @param instance an application instance of type {@link Instance} to be removed
     * @return true if remove process is success; else false
     */
    boolean removeRegistry(Instance instance);
}

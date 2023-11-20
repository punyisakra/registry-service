package com.assignment.coda.registryservice.registry.service;

import com.assignment.coda.registryservice.registry.dto.Instance;
import com.assignment.coda.registryservice.registry.dto.RegistryEvent;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A service class providing an implementation to {@link RegistryService}
 * to handle registry logic
 */
@Service
public class RegistryServiceImpl implements RegistryService {

    private final List<Instance> instanceList = Collections.synchronizedList(new LinkedList<>());

    private final Logger logger = LoggerFactory.getLogger(RegistryServiceImpl.class);

    private RabbitService rabbitService;

    @Autowired
    public RegistryServiceImpl(RabbitService rabbitService) {
        this.rabbitService = rabbitService;
    }

    /**
     * Register the input instance by adding the instance to the registry list.
     * If success, publish the {@link RegistryEvent} to the RabbitMQ.
     * @param instance an application instance of type {@link Instance} to be registered
     * @return true if register process is success; else false
     */
    @Override
    public boolean register(Instance instance) {
        if (Strings.isBlank(instance.getName())
                || Strings.isBlank(instance.getPort())) {
            return false;
        }
        instanceList.add(instance);
        logger.info("Registered instance: {}", instance);
        rabbitService.sent(new RegistryEvent("add", instance));
        return true;
    }

    /**
     * Get all the instances that have been registered with the registry.
     * Will return empty {@link List} if no instance has registered.
     * @return a list of type {@link List<Instance>} represented all {@link Instance}s which were
     * registered with the registry
     */
    @Override
    public List<Instance> getRegistryList() {
        return instanceList;
    }

    /**
     * Remove the input instance from the registry list.
     * If success, publish the {@link RegistryEvent} to the RabbitMQ.
     * @param instance an application instance of type {@link Instance} to be removed
     * @return true if remove process is success; else false
     */
    @Override
    public boolean removeRegistry(Instance instance) {
        boolean isRemoved = instanceList.remove(instance);
        if (isRemoved) {
            logger.info("Removed instance: {}", instance);
            rabbitService.sent(new RegistryEvent("remove", instance));
        }
        else {
            logger.error("Remove failed, instance: {}", instance);
        }
        return isRemoved;
    }
}

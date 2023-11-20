package com.assignment.coda.registryservice.registry.service;

import com.assignment.coda.registryservice.registry.model.Instance;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class RegistryServiceImpl implements RegistryService {

    private final List<Instance> instanceList = Collections.synchronizedList(new LinkedList<>());

    private final Logger logger = LoggerFactory.getLogger(RegistryServiceImpl.class);

    @Override
    public boolean register(Instance instance) {
        if (Strings.isBlank(instance.getName())
                || Strings.isBlank(instance.getPort())) {
            return false;
        }
        instanceList.add(instance);
        logger.info("Registered instance: {}", instance);
        return true;
    }

    @Override
    public List<Instance> getRegistryList() {
        return instanceList;
    }

    @Override
    public boolean removeRegistry(Instance instance) {
        logger.info("Remove instance: {}", instance);
        return instanceList.remove(instance);
    }
}

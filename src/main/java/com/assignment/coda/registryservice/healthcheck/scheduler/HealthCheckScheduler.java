package com.assignment.coda.registryservice.healthcheck.scheduler;

import com.assignment.coda.registryservice.registry.dto.Instance;
import com.assignment.coda.registryservice.healthcheck.service.HealthCheckService;
import com.assignment.coda.registryservice.registry.service.RegistryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * A scheduler class to perform the health check processes
 */
@Component
public class HealthCheckScheduler {

    private final Logger logger = LoggerFactory.getLogger(HealthCheckScheduler.class);

    private RegistryService registryService;

    private HealthCheckService healthCheckService;

    @Autowired
    public HealthCheckScheduler(RegistryService registryService, HealthCheckService healthCheckService) {
        this.registryService = registryService;
        this.healthCheckService = healthCheckService;
    }

    /**
     * Perform health check on every record in the registry list to maintain the accessibility.
     * If the health check fails, the instance is removed from the registry list
     */
    @Scheduled(initialDelayString = "PT10S", fixedDelayString = "PT30S")
    public void healthCheck() {
        final List<Instance> instanceList = registryService.getRegistryList().stream().toList();
        logger.debug("Schedule healthcheck on instance list, size: {}", instanceList.size());
        if (instanceList.isEmpty()) return;

        for (Instance instance: instanceList) {
            if (!healthCheckService.healthCheck(instance))
                registryService.removeRegistry(instance);
        }
        logger.debug("Done schedule healthcheck");
    }
}

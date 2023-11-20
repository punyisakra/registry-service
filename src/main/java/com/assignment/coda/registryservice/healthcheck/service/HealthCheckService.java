package com.assignment.coda.registryservice.healthcheck.service;

import com.assignment.coda.registryservice.registry.model.Instance;
import org.springframework.web.client.RestClientException;

public interface HealthCheckService {
    boolean healthCheck(Instance instance) throws RestClientException;

    boolean healthCheckRecover(RestClientException e, Instance instance);
}

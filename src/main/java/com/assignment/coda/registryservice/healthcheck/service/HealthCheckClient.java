package com.assignment.coda.registryservice.healthcheck.service;

import com.assignment.coda.registryservice.healthcheck.dto.HealthStatus;
import com.assignment.coda.registryservice.registry.dto.Instance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

/**
 * A client interface implemented by {@link HealthCheckClientImpl}
 * to perform health check on registry list
 */
public interface HealthCheckClient {

    /**
     * Perform health check to the input instance
     * @param instance an instance of type {@link Instance} in registry list to be checked
     * @return a status {@link HealthStatus} of the input instance
     * @throws RestClientException when there is any error when sending GET HTTP request
     */
    ResponseEntity<HealthStatus> healthCheck(Instance instance) throws RestClientException;
}

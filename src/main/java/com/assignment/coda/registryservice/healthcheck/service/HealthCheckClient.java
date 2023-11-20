package com.assignment.coda.registryservice.healthcheck.service;

import com.assignment.coda.registryservice.healthcheck.dto.HealthStatus;
import com.assignment.coda.registryservice.registry.dto.Instance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

public interface HealthCheckClient {
    ResponseEntity<HealthStatus> healthCheck(Instance instance) throws RestClientException;
}

package com.assignment.coda.registryservice.healthcheck.service;

import com.assignment.coda.registryservice.healthcheck.model.HealthStatus;
import com.assignment.coda.registryservice.registry.model.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
public class HealthCheckServiceImpl implements HealthCheckService {

    private final Logger logger = LoggerFactory.getLogger(HealthCheckServiceImpl.class);

    private HealthCheckClient healthCheckClient;

    @Autowired
    public HealthCheckServiceImpl(HealthCheckClient healthCheckClient) {
        this.healthCheckClient = healthCheckClient;
    }

    @Override
    @Retryable(retryFor = RestClientException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public boolean healthCheck(Instance instance) throws RestClientException {
        ResponseEntity<HealthStatus> status = healthCheckClient.healthCheck(instance);
        if (status.getStatusCode().equals(HttpStatus.OK) && "UP".equals(status.getBody().getStatus()))
            return true;
        throw new RestClientException("instance status: DOWN");
    }

    @Override
    @Recover
    public boolean healthCheckRecover(RestClientException e, Instance instance) {
        logger.debug("Reach max retry for healthcheck instance: {}", instance);
        return false;
    }
}

package com.assignment.coda.registryservice.healthcheck.service;

import com.assignment.coda.registryservice.healthcheck.dto.HealthStatus;
import com.assignment.coda.registryservice.registry.dto.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

/**
 * A service class providing an implementation to {@link HealthCheckService}
 * to handle health check's call logic
 */
@Service
public class HealthCheckServiceImpl implements HealthCheckService {

    private final Logger logger = LoggerFactory.getLogger(HealthCheckServiceImpl.class);

    private HealthCheckClient healthCheckClient;

    @Autowired
    public HealthCheckServiceImpl(HealthCheckClient healthCheckClient) {
        this.healthCheckClient = healthCheckClient;
    }

    /**
     * Perform health check on every instance in registry list synchronously.
     * If an {@link RestClientException} is thrown, the method with retry with the same argument.
     * If the maxAttempts is reached, the recovery method {@link #healthCheckRecover(RestClientException, Instance) healthCheckRecover} will be executed.
     * @param instance an instance of type {@link Instance} in registry list to be checked
     * @return true if {@link HealthStatus#getStatus()}} is "UP"; else throws AmqpException
     * @throws AmqpException when there is any error with the endpoint
     */
    @Override
    @Retryable(retryFor = RestClientException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public boolean healthCheck(Instance instance) throws RestClientException {
        ResponseEntity<HealthStatus> status = healthCheckClient.healthCheck(instance);
        if (status.getStatusCode().equals(HttpStatus.OK) && "UP".equals(status.getBody().getStatus()))
            return true;
        throw new RestClientException("instance status: DOWN");
    }

    /**
     * Recovery method for {@link #healthCheck(Instance) healthCheck}
     * @param e         an exception {@link RestClientException} of the last try
     * @param instance  an instance of type {@link Instance} in registry list to be checked
     * @return false
     */
    @Override
    @Recover
    public boolean healthCheckRecover(RestClientException e, Instance instance) {
        logger.debug("Reach max retry for healthcheck instance: {}", instance);
        return false;
    }
}

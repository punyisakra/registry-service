package com.assignment.coda.registryservice.healthcheck.service;

import com.assignment.coda.registryservice.healthcheck.dto.HealthStatus;
import com.assignment.coda.registryservice.registry.dto.Instance;
import org.springframework.amqp.AmqpException;
import org.springframework.web.client.RestClientException;

/**
 * A service interface implemented by {@link HealthCheckServiceImpl}
 * to handle health check's call logic
 */
public interface HealthCheckService {

    /**
     * Perform health check on every instance in registry list synchronously.
     * If an {@link RestClientException} is thrown, the method with retry with the same argument.
     * If the maxAttempts is reached, the recovery method {@link #healthCheckRecover(RestClientException, Instance) healthCheckRecover} will be executed.
     * @param instance an instance of type {@link Instance} in registry list to be checked
     * @return true if {@link HealthStatus#getStatus()}} is "UP"; else throws AmqpException
     * @throws AmqpException when there is any error with the endpoint
     */
    boolean healthCheck(Instance instance) throws RestClientException;

    /**
     * Recovery method for {@link #healthCheck(Instance) healthCheck}
     * @param e         an exception {@link RestClientException} of the last try
     * @param instance  an instance of type {@link Instance} in registry list to be checked
     * @return false
     */
    boolean healthCheckRecover(RestClientException e, Instance instance);
}

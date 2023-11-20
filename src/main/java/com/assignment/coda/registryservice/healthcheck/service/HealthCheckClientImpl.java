package com.assignment.coda.registryservice.healthcheck.service;

import com.assignment.coda.registryservice.healthcheck.dto.HealthStatus;
import com.assignment.coda.registryservice.registry.dto.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * A client class providing an implementation to {@link HealthCheckClient}
 * to perform health check on registry list
 */
@Service
public class HealthCheckClientImpl implements HealthCheckClient {

    private final Logger logger = LoggerFactory.getLogger(HealthCheckClientImpl.class);

    private RestTemplate restTemplate;

    @Autowired
    public HealthCheckClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Perform health check to the input instance
     * @param instance an instance of type {@link Instance} in registry list to be checked
     * @return a status {@link HealthStatus} of the input instance
     * @throws RestClientException when there is any error when sending GET HTTP request
     */
    @Override
    public ResponseEntity<HealthStatus> healthCheck(Instance instance) throws RestClientException {
        String url = String.format("http://localhost:%s/actuator/health", instance.getPort());
        logger.debug("{} {}", RequestMethod.GET, url);
        return restTemplate.getForEntity(url, HealthStatus.class);
    }
}

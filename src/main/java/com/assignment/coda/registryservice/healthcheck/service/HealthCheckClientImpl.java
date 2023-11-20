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

@Service
public class HealthCheckClientImpl implements HealthCheckClient {

    private final Logger logger = LoggerFactory.getLogger(HealthCheckClientImpl.class);

    private RestTemplate restTemplate;

    @Autowired
    public HealthCheckClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<HealthStatus> healthCheck(Instance instance) throws RestClientException {
        String url = String.format("http://localhost:%s/actuator/health", instance.getPort());
        logger.debug("{} {}", RequestMethod.GET, url);
        return restTemplate.getForEntity(url, HealthStatus.class);
    }
}

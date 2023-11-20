package com.assignment.coda.registryservice.healthcheck.service;

import com.assignment.coda.registryservice.healthcheck.model.HealthStatus;
import com.assignment.coda.registryservice.registry.model.Instance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class HealthCheckClientTest {

    @Mock
    private RestTemplate restTemplateMock;

    @InjectMocks
    private HealthCheckClientImpl healthCheckClient;

    @Test
    public void healthCheck_givenAnInstance_callHealthCheckWithTheInstanceInformation() {
        Instance instance = new Instance("name", "3333");
        String url = "http://localhost:3333/actuator/health";
        ResponseEntity<HealthStatus> status = ResponseEntity.ok(new HealthStatus("UP"));

        when(restTemplateMock.getForEntity(url, HealthStatus.class)).thenReturn(status);
        ResponseEntity<HealthStatus> response = healthCheckClient.healthCheck(instance);

        assertThat(response, notNullValue());
        assertThat(response, is(status));
        verify(restTemplateMock, times(1))
                .getForEntity(url, HealthStatus.class);
    }

    @Test
    public void healthCheck_error_delegateTheThrownError() {
        Instance instance = new Instance("name", "3333");
        String url = "http://localhost:3333/actuator/health";

        when(restTemplateMock.getForEntity(url, HealthStatus.class)).thenThrow(new RestClientException("err"));

        assertThrows(RestClientException.class, () -> healthCheckClient.healthCheck(instance));
        verify(restTemplateMock, times(1))
                .getForEntity(url, HealthStatus.class);
    }
}
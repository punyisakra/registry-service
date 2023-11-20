package com.assignment.coda.registryservice.healthcheck.service;

import com.assignment.coda.registryservice.healthcheck.dto.HealthStatus;
import com.assignment.coda.registryservice.registry.dto.Instance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class HealthCheckServiceTest {

    @Mock
    private HealthCheckClient healthCheckClientMock;

    @InjectMocks
    private HealthCheckServiceImpl healthCheckService;

    @Test
    public void healthCheck_theInstanceIsUp_returnTrue() {
        Instance instance = new Instance("name", "1234");
        ResponseEntity<HealthStatus> response = ResponseEntity.ok(new HealthStatus("UP"));

        when(healthCheckClientMock.healthCheck(instance)).thenReturn(response);
        boolean isUp = healthCheckService.healthCheck(instance);

        assertThat(isUp, is(true));
    }

    @Test
    public void healthCheck_theInstanceIsDown_throwException() {
        Instance instance = new Instance("name", "1234");
        ResponseEntity<HealthStatus> response = ResponseEntity.ok(new HealthStatus("Down"));

        when(healthCheckClientMock.healthCheck(instance)).thenReturn(response);

        assertThrows(RestClientException.class, () -> healthCheckService.healthCheck(instance));
    }

    @Test
    public void healthCheckRecover_returnFalse() {
        Instance instance = new Instance("name", "port");
        RestClientException e = new RestClientException("instance status: DOWN");

        boolean isUp = healthCheckService.healthCheckRecover(e, instance);

        assertThat(isUp, is(false));
    }
}
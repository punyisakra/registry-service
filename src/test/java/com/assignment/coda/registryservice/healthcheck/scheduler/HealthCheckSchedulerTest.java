package com.assignment.coda.registryservice.healthcheck.scheduler;

import com.assignment.coda.registryservice.healthcheck.service.HealthCheckService;
import com.assignment.coda.registryservice.registry.model.Instance;
import com.assignment.coda.registryservice.registry.service.RegistryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class HealthCheckSchedulerTest {

    @Mock
    private RegistryService registryServiceMock;

    @Mock
    private HealthCheckService healthCheckServiceMock;

    @InjectMocks
    private  HealthCheckScheduler healthCheckScheduler;

    @Test
    public void healthCheck_emptyRegistryList_skipHealthCheck() {
        when(registryServiceMock.getRegistryList()).thenReturn(new ArrayList<>());
        healthCheckScheduler.healthCheck();

        verify(healthCheckServiceMock, times(0)).healthCheck(any(Instance.class));
    }

    @Test
    public void healthCheck_registryListWithManyInstances_performHealthCheckForEachInstance() {
        Instance instance1 = new Instance("name1", "1111");
        Instance instance2 = new Instance("name2", "2222");
        Instance instance3 = new Instance("name3", "3333");
        List<Instance> instanceList = Arrays.asList(instance1,instance2, instance3);

        when(registryServiceMock.getRegistryList()).thenReturn(instanceList);
        when(healthCheckServiceMock.healthCheck(instance1)).thenReturn(true);
        when(healthCheckServiceMock.healthCheck(instance2)).thenReturn(false);
        when(healthCheckServiceMock.healthCheck(instance3)).thenReturn(true);
        healthCheckScheduler.healthCheck();

        verify(healthCheckServiceMock, times(1)).healthCheck(instance1);
        verify(healthCheckServiceMock, times(1)).healthCheck(instance2);
        verify(healthCheckServiceMock, times(1)).healthCheck(instance3);
        verify(registryServiceMock, times(1)).removeRegistry(instance2);
    }


}
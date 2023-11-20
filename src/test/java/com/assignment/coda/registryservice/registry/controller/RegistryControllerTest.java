package com.assignment.coda.registryservice.registry.controller;

import com.assignment.coda.registryservice.registry.model.Instance;
import com.assignment.coda.registryservice.registry.service.RegistryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
class RegistryControllerTest {

    @Mock
    private RegistryService serviceMock;

    @InjectMocks
    private RegistryController controller;

    @Test
    public void register_registerSuccess_returnSuccessCode() {
        Instance instance = new Instance("name", "port");

        Mockito.when(serviceMock.register(instance)).thenReturn(true);
        ResponseEntity<String> response = controller.register(instance);

        Mockito.verify(serviceMock, Mockito.times(1)).register(instance);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void register_registerFailed_returnFailCode() {
        Instance instance = new Instance("name", "");

        Mockito.when(serviceMock.register(instance)).thenReturn(false);
        ResponseEntity<String> response = controller.register(instance);

        Mockito.verify(serviceMock, Mockito.times(1)).register(instance);
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void getRegistryList_listHasInstances_returnTheList() {
        List<Instance> instanceList = new ArrayList<>();
        instanceList.add(new Instance("name1", "port1"));
        instanceList.add(new Instance("name2", "port2"));

        Mockito.when(serviceMock.getRegistryList()).thenReturn(instanceList);
        List<Instance> responseList = controller.getRegistryList();

        Mockito.verify(serviceMock, Mockito.times(1)).getRegistryList();
        assertThat(responseList, notNullValue());
        assertThat(responseList.size(), is(2));

        Instance instance1 = responseList.get(0);
        assertThat(instance1.getName(), is("name1"));
        assertThat(instance1.getPort(), is("port1"));

        Instance instance2 = responseList.get(1);
        assertThat(instance2.getName(), is("name2"));
        assertThat(instance2.getPort(), is("port2"));
    }

    @Test
    public void getRegistryList_emptyList_returnTheList() {
        List<Instance> instanceList = new ArrayList<>();

        Mockito.when(serviceMock.getRegistryList()).thenReturn(instanceList);
        List<Instance> responseList = controller.getRegistryList();

        Mockito.verify(serviceMock, Mockito.times(1)).getRegistryList();
        assertThat(responseList, notNullValue());
        assertThat(responseList.size(), is(0));
    }
}
package com.assignment.coda.registryservice.service;

import com.assignment.coda.registryservice.model.Instance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
class RegistryServiceTest {

    @InjectMocks
    private RegistryServiceImpl service;

    @Test
    void register_validInstance_ReturnTrue() {
        Instance instance = new Instance("name", "port");

        boolean isRegistered = service.register(instance);

        assertThat(isRegistered, is(true));
    }

    @Test
    void register_blankName_ReturnFalse() {
        Instance instance = new Instance(" ", "port");

        boolean isRegistered = service.register(instance);

        assertThat(isRegistered, is(false));
    }

    @Test
    void register_blankPort_ReturnFalse() {
        Instance instance = new Instance("name", " ");

        boolean isRegistered = service.register(instance);

        assertThat(isRegistered, is(false));
    }

    @Test
    void getRegistryList_hasInstanceRegistered_returnTheRegisteredList() {
        service.register(new Instance("name1", "port1"));
        service.register(new Instance("name2", "port2"));

        List<Instance> instanceList = service.getRegistryList();

        assertThat(instanceList, notNullValue());
        assertThat(instanceList.size(), is(2));

        Instance instance1 = instanceList.get(0);
        assertThat(instance1.getName(), is("name1"));
        assertThat(instance1.getPort(), is("port1"));

        Instance instance2 = instanceList.get(1);
        assertThat(instance2.getName(), is("name2"));
        assertThat(instance2.getPort(), is("port2"));
    }

    @Test
    void getRegistryList_emptyList_returnEmptyList() {
        List<Instance> instanceList = service.getRegistryList();

        assertThat(instanceList, notNullValue());
        assertThat(instanceList.size(), is(0));
    }
}
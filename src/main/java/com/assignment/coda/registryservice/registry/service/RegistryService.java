package com.assignment.coda.registryservice.registry.service;

import com.assignment.coda.registryservice.registry.model.Instance;

import java.util.List;

public interface RegistryService {
    boolean register(Instance instance);

    List<Instance> getRegistryList();

    boolean removeRegistry(Instance instance);
}

package com.assignment.coda.registryservice.service;

import com.assignment.coda.registryservice.model.Instance;

import java.util.List;

public interface RegistryService {
    boolean register(Instance instance);

    List<Instance> getRegistryList();
}

package com.assignment.coda.registryservice.registry.controller;

import com.assignment.coda.registryservice.registry.dto.Instance;
import com.assignment.coda.registryservice.registry.service.RegistryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegistryController {

    private final Logger logger = LoggerFactory.getLogger(RegistryController.class);

    private RegistryService registryService;

    @Autowired
    public RegistryController(RegistryService registryService) {
        this.registryService = registryService;
    }

    @RequestMapping(
            value = "/registries",
            method = RequestMethod.POST)
    public ResponseEntity<String> register(@Validated @RequestBody Instance instance) {
        logger.info("{} /registers, REQUEST PAYLOAD: {}", RequestMethod.POST, instance);
        ResponseEntity<String> response = registryService.register(instance)
                ? ResponseEntity.ok("success")
                : ResponseEntity.badRequest().body("failed");
        logger.info("RESPONSE: {}", response.getStatusCode());
        return response;
    }

    @RequestMapping(
            value = "/registries",
            method = RequestMethod.GET)
    public List<Instance> getRegistryList() {
        logger.info("{} /registers", RequestMethod.GET);
        List<Instance> registryList = registryService.getRegistryList();
        logger.info("RESPONSE: {}", registryList);
        return registryList;
    }
}

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

/**
 * Registry API Controller for handling registry HTTP request
 * */
@RestController
public class RegistryController {

    private final Logger logger = LoggerFactory.getLogger(RegistryController.class);

    private RegistryService registryService;

    @Autowired
    public RegistryController(RegistryService registryService) {
        this.registryService = registryService;
    }

    /**
     * Register POST API: accept a running instance as request body and register it in the
     * registry.  Will return {@link org.springframework.http.HttpStatus} 400: Bad Request if
     * the register process is failed.
     * @param instance an application instance of type {@link Instance} to be registered
     * @return the {@link String} result of register process, wrapped with HttpStatus
     */
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

    /**
     * Register GET API: get all the instance list that have been registered with the registry.
     * Will return empty {@link List} if no instance has registered.
     * @return a list of type {@link List<Instance>} represented all {@link Instance}s which were
     * registered with the registry
     */
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

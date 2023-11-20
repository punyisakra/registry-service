package com.assignment.coda.registryservice.healthcheck.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * Represent a health check's status of the {@link com.assignment.coda.registryservice.registry.dto.Instance Instance}
 * when maintaining the registry list
 */
@Component
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class HealthStatus {
    /**
     * Status string indicating the status of the instance,
     * such as "UP" or "DOWN"
     */
    private String status;
}

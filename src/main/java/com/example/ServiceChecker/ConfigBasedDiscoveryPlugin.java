package com.example.ServiceChecker;
// NOTE: Use your actual package name (e.g., com.howtodoinjava.kafka) if different.

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ConfigBasedDiscoveryPlugin implements ServiceDiscoveryPlugin {

    // These fields are final and MUST be initialized in the constructor
    private final ExternalServiceProperties properties;
    private final HealthCheckExecutorService healthCheckExecutorService;

    /**
     * FIX: Constructor Injection via @Autowired
     * This constructor takes the required dependencies as arguments,
     * which Spring automatically provides (initializes them), and then
     * assigns them to the final fields.
     */
    @Autowired
    public ConfigBasedDiscoveryPlugin(
            ExternalServiceProperties properties,
            HealthCheckExecutorService healthCheckExecutorService) {
        // Initialization of final fields happens here
        this.properties = properties;
        this.healthCheckExecutorService = healthCheckExecutorService;
    }

    @Override
    public Map<String, ? extends HealthContributor> discoverServices() {
        if (properties.getServices() == null || properties.getServices().isEmpty()) {
            return Map.of();
        }

        return properties.getServices().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (HealthContributor) createIndicator(entry.getKey(), entry.getValue())
                ));
    }

    private HealthIndicator createIndicator(String name, ExternalServiceProperties.ServiceConfig config) {
        return () -> healthCheckExecutorService.checkExternalService(name, config);
    }
}
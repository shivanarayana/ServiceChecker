package com.example.ServiceChecker;

import org.springframework.boot.actuate.health.HealthContributor;
import java.util.Map;

/**
 * Defines the contract for all service discovery plugins.
 */
public interface ServiceDiscoveryPlugin {

    /**
     * Discovers and returns a Map of named health contributors (HealthIndicators).
     * The map key is the service name, and the value is the contributor object.
     *
     * @return A map of service names to contributors.
     */
    Map<String, ? extends HealthContributor> discoverServices();
}
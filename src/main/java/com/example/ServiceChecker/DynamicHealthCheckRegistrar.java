package com.example.ServiceChecker;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.actuate.health.HealthContributorRegistry;
import org.springframework.stereotype.Component;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired; // Ensure this is present

@Component
public class DynamicHealthCheckRegistrar implements InitializingBean {

    private final HealthContributorRegistry registry;
    private final List<ServiceDiscoveryPlugin> discoveryPlugins;

    @Autowired
    public DynamicHealthCheckRegistrar(
            HealthContributorRegistry registry,
            List<ServiceDiscoveryPlugin> discoveryPlugins) {
        this.registry = registry;
        this.discoveryPlugins = discoveryPlugins;
    }

    @Override
    public void afterPropertiesSet() {
        // 1. Iterate over all discovered plugins
        discoveryPlugins.stream()
                // 2. Ask each plugin for its Map of named services (Map<String, HealthContributor>)
                .flatMap(plugin -> plugin.discoverServices().entrySet().stream())
                // 3. Register each entry into the main registry using the name and the contributor
                .forEach(entry -> registry.registerContributor(entry.getKey(), entry.getValue()));
    }
}
package com.example.ServiceChecker;
// NOTE: Use your actual package name (e.g., com.howtodoinjava.kafka)

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Simplified Plugin for Test App 1.
 * It now only includes HTTP health checks for its dependencies:
 * Test App 2 (Internal), Google, and Facebook (External).
 */
@Component
public class TestApp1DependencyPlugin implements ServiceDiscoveryPlugin {

    private final HealthCheckExecutorService httpExecutor;

    @Autowired
    public TestApp1DependencyPlugin(HealthCheckExecutorService httpExecutor) {
        this.httpExecutor = httpExecutor;
    }

    @Override
    public Map<String, ? extends HealthContributor> discoverServices() {
        return Stream.of(
                        // 1. Dependency: Google (External HTTP Check)
                        Map.of("test-app1-google-check", createExternalHttpIndicator("https://www.google.com", "EXTERNAL_INTERNET_CHECK")),

                        // 2. Dependency: Facebook (External HTTP Check)
                        Map.of("test-app1-facebook-check", createExternalHttpIndicator("https://www.facebook.com", "EXTERNAL_INTERNET_CHECK"))
                ).flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    // Reusable Method for External/Internal HTTP Checks
    private HealthIndicator createExternalHttpIndicator(String url, String flow) {
        ExternalServiceProperties.ServiceConfig config = new ExternalServiceProperties.ServiceConfig();
        config.setUrl(url);
        config.setFlow(flow);

        // Reuse the core HTTP executor service
        return () -> httpExecutor.checkExternalService(url, config);
    }
}
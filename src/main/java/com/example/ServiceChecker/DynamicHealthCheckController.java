package com.example.ServiceChecker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DynamicHealthCheckController {

    @Autowired
    private HealthCheckExecutorService healthCheckExecutorService;

    // It accepts a JSON list of URLs (Strings), iterates through them using a stream,
    // and converts them into a list of UrlHealthResult objects.
    @PostMapping("/actuator/dynamichealthbulk")
    public ResponseEntity<List<UrlHealthResult>> dynamicHealthCheckBulk(
            @RequestBody List<String> callerUrls) {

        List<UrlHealthResult> results = callerUrls.stream()
                .map(this::runSingleCheckClean)
                .toList();

        return ResponseEntity.ok(results);
    }

    // A private helper method that orchestrates the check for a single URL.
    // We can move this to service layer
    private UrlHealthResult runSingleCheckClean(String callerUrl) {
        try {
            String serviceName = callerUrl.contains("localhost")
                    ? "test-manager-dynamic"
                    : "dynamic-service";

            // Create config object on the fly
            ExternalServiceProperties.ServiceConfig dynamicConfig =
                    new ExternalServiceProperties.ServiceConfig();

            dynamicConfig.setUrl(callerUrl);
            dynamicConfig.setFlow("DYNAMIC_HEALTH_CHECK");

            Health health = healthCheckExecutorService.checkExternalService(serviceName, dynamicConfig);

            String status = health.getStatus().equals(org.springframework.boot.actuate.health.Status.UP)
                    ? "UP"
                    : "DOWN";

            return new UrlHealthResult(callerUrl, status);

        } catch (Exception e) {
            return new UrlHealthResult(callerUrl, "DOWN");
        }
    }
}
package com.example.ServiceChecker;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

// The @ConfigurationProperties is needed for the ConfigBasedDiscoveryPlugin
@ConfigurationProperties(prefix = "external-services")
public class ExternalServiceProperties {

    // Map to hold services defined in application.yaml
    private Map<String, ServiceConfig> services;

    public Map<String, ServiceConfig> getServices() {
        return services;
    }

    public void setServices(Map<String, ServiceConfig> services) {
        this.services = services;
    }

    // ... ServiceConfig inner class from previous turns must also be here ...
    public static class ServiceConfig {
        private String url;
        private String flow;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getFlow() {
            return flow;
        }

        public void setFlow(String flow) {
            this.flow = flow;
        }
    }
}
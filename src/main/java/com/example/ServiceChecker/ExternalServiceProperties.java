package com.example.ServiceChecker;

public class ExternalServiceProperties {

    //It acts as a structured parameter object (a container) holding the url and flow strings.
    //This allows the HealthCheckExecutorService to accept a consistent object format rather than raw strings.

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
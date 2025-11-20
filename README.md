This is a lightweight Spring Boot utility designed to perform bulk health checks. 
It exposes a REST API endpoint that accepts a list of URLs, attempts to connect to each one via HTTP, and returns a status report ("UP" or "DOWN") for every URL provided. 
It reuses Spring Boot's robust Health object logic but applies it dynamically to user-supplied URLs rather than static configuration files.




curl --location 'http://localhost:9001/actuator/dynamichealthbulk' \
--header 'Content-Type: application/json' \
--data '[
"https://www.google.com",
"https://www.github.com",
"http://localhost:9999"
]'




True Decoupling: Test App 1 team can change their plugin (e.g., update the MongoDB check) and deploy a new version of their Plugin JAR without asking the Service Checker team to modify or redeploy anything.	Complexity: Requires setting up a multi-module build (Maven/Gradle) to ensure the Service Checker project includes the Plugin JAR as a dependency.
Ownership: The team responsible for the services (Test App 1) is responsible for writing and maintaining their own health check logic.	Class Path Management: The ServiceChecker needs to ensure the plugin JAR is on its classpath at runtime.
Clean Core: The ServiceChecker remains "closed for modification" (the core registrar logic never changes).
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
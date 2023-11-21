# registry-service
Registry Service for handling service (a.k.a. instance) registration, maintaining the registry list, and handling health check for each instance. Consisting of 2 modules: registry and healthcheck. </br></br>

### Registry Module
Handle HTTP request for Registry API and publish event to **routing-service** when registry list is modified

### Healthcheck Module
Handle logic to maintain the registry list by performing healthcheck to each instance in the list

</br>

## System Flow
### Register service to registry

```mermaid
sequenceDiagram
    participant simple-api-service
    participant registry-service
    participant routing-service
    simple-api-service->>simple-api-service: handle application event when initialized
    simple-api-service->>registry-service: POST: /registries {application name, port}
    registry-service->>registry-service: add application to registry list
    registry-service->>routing-service: publish "add" event
    registry-service->>simple-api-service: HTTP Response 200: OK {"success"}
```

### Maintain and healthcheck registry list

```mermaid
sequenceDiagram
    participant registry-service
    participant simple-api-service(1)
    participant simple-api-service(2)
    participant routing-service
    registry-service->>registry-service: schedule healthcheck
    registry-service->>registry-service: get registry list
    registry-service->>simple-api-service(1): GET: /actuator/health
    simple-api-service(1)->>registry-service: HTTP Reponse 200: OK {"UP"}
    loop retry
        registry-service->>simple-api-service(2): GET: /actuator/health
        simple-api-service(2)->>registry-service: HTTP Reponse 200: OK {"DOWN"}
    end
    registry-service->>registry-service: remove simple-api-service(2) from registry list
    registry-service->>routing-service: publish "remove" event
```

For more information on other flows and services, see also [simple-api-service](https://github.com/punyisakra/simple-api-service) and [routing-service](https://github.com/punyisakra/routing-service)

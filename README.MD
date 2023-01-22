# CRUD Operation For Microservices

This service represent CRUD operation which covers the logistics world scenarios. It manages logictics for example, transport, utilize vehicles, trailers, etc. to handle their daily workload. These assets are managed by the organizations within fleets.

## Prerequisites
   • Kotlin 1.4 \
   • IDE  \
   • Spring boot \
   • Docker \
   • MySQL \
   • JVM - 1.8 \
   • Flyway schema \
   • Postman 

## Use cases
Fleet service offers a REST API to handle these below use cases:
1. CRUD operations on fleets
2. Add assets to a fleet
3. Remove assets from a fleet
4. Search for an asset by its VIN 
5. Search for all assets in a fleet
   
## Constraints
   • An asset is identified by a Vehicle Identification Number (VIN) • The same asset can only occur within one fleet

## Swagger documentation urls
**http://localhost:8080/api/swagger-ui.html#/** \
**http://localhost:8080/api/v2/api-docs**

## Steps to follow to run the application

1. Clone the project
2. Pre-install kotlin, jvm and docker
3. Run ./gradlw clean build
4. Go to scripts folder (cd scripts)
5. Run docker compose file (docker-compose up -d)
6. Run the application
7. Import postman collection and use the api

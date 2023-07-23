# API - challenge Fivvy

## Description

Esta API está diseñada para proporcionar funcionalidades de visualización, modificación y registro de los términos 
y condiciones que se tienen y los terminos y condiciones que un usuario ha aceptado.

## Technologies Used

The API was developed using the following technologies.

- Java 17
- Spring Boot 2.7.14
- MongoDB
- Lombok
- Java Validation
- Swagger
- Mongo DB embed  
- Gradle
- Docker

## Docker Compose

"The project uses Docker to facilitate its deployment. The docker-compose.yml file is located at the root of the project
and can be used to launch the following services:"
    
    - MongoDB
    - API Fivvy

"To start the services, run the following command:"

    docker compose up

This command launches a MongoDB instance and deploys the latest version of the Fivvy API, which has been previously 
built and uploaded to the Docker repository with the latest master changes

The API will be available at the following URL: http://127.0.0.1:8080


If you prefer to run the project locally, you can start a MongoDB container using the following command and launch 
the project with the local Spring profile:

    docker run -d -p 27017-27019:27017-27019 --name mongodb mongo


## Potential Enhancements

Some areas where this project could be enhanced include:

Moving the database connection properties to environment variables for increased security.
Increasing the number of logs throughout the API to provide a more detailed user flow tracking.
Creating unit tests for all classes to achieve the desired coverage level



    

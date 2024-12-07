# Microservices Final Project

## Deployed version access
-   http://rozpadek.online
-   Deployed on Digital Ocean Droplet

## Installation
1. Clone the repository
2. Replace placeholders for api keys/secrets in docker-compose.yaml
3. Run `docker compose up -d`
4. Access application on port 80

## Description
This application allows users to post snowmens they find in their local area. A user can post a photo of the snowmen and its specific location to a board that lists the snowmen in the city. Other users can then report if they visit the snowman or report that its missing if it already melted. This application has a frontend developed in React. It also has two microservices that exist to provide functionality. These microservices were developed using the Spring Boot framework. The Posting microservice uses an embedded instance of the H2 database. The application uses AWS S3 to store and serve the images the users upload. Access control to the endpoints is secured using Spring Security and Google Oauth as the only avaliable login method. Metrics are tracked for the REST endpoints using Spring Actuator. Email notifications are sent in the notify microservice by calling mailjets 3rd party emailing api. The posting microservice produces events using Apache Kafka which is then consumed by the notify microservice. This project is containerized using docker. Nginx is used to store the static React files and works as a reverse proxy to serve the posting microservice's endpoints to the frontend on port 80.

## Microservices

### Posting Microservice

#### Endpoints
-   GET /oauth2/authorization/google 
    *   Google Oauth Login Endpoint
-   GET /api/city/{city} 
    *   Returns snowmen posted in the {city}
    *   Options for {city} are
        1. Milwaukee
        2. Chicago
        3. Dubai
-   POST /api/subscribe/{city}
    * Signs user up for email notications whenever a new snowman post is posted
    *   Options for {city} are
        1. Milwaukee
        2. Chicago
        3. Dubai
-   POST /api/missing
    * Increments the amount of times someone reports a snowman missing
    * Request Body JSON - {"id" : id}
    * id is the snowman's ID
-   POST /api/visit
    * Increments the amount of times someone reports a visiting a snowman
    * Request Body JSON - {"id" : id}
    * id is the snowman's ID
-   POST /api/post
    * Posts a new snowman
    * Request body is FormData
        1. image : blob of the snowman picture
        2. city : City snowman is posted in, see options above
        3. location : Specific location string provided in form, for example "Dierks Hall"
-   GET /actuator/metrics/http.server.requests?uri={uri}
    * Provides stats such as amount of times endpoint is visited, total time proccessing endpoint, and longest time it took to process endpoint
    * Metrics tracked through Spring Actuator
    * uri is in the form `/api/visit` for example if you wanted to see the metrics for the visiting endpoint


### Notify Microservice
#### Kafka Topics
-   Milwaukee
    * Recieves a message in the form ``<EMAIL ADDRESS>\n<EMAIL BODY>``
    * Sends a new snowman in Milwakee email 
-   Chicago
    * Recieves a message in the form ``<EMAIL ADDRESS>\n<EMAIL BODY>``
    * Sends a new snowman in Chicago email
-   Dubai
    * Recieves a message in the form ``<EMAIL ADDRESS>\n<EMAIL BODY>``
    * Sends a new snowman in Dubai email

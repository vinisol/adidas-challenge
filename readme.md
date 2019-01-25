## Itineraries

Given an origin city the application will return a list of itineraries, one based in the less number of
connections and the second based in the less time.

### Architecture

<img width="880" alt="Architecture" src="https://github.com/vinisol/adidas-challenge/blob/master/architecture.png">

### Technologies

* Spring Boot
* Spring Cloud Netflix (Eureka, Hystrix, Zuul)
* Spring Web MVC
* Spring Data JPA
* SpringFox Swagger
* Lombok
* Gradle

### Services

#### Itinerary service
Service responsible for retrieving itineraries stored in the database

Method	| Path	| Description	
------------- | ------------------------- | ------------- |
GET	| /itineraries	| Return all itineraries

#### Planning Itinerary service
Service responsible for planning the shortest itinerary according to the criteria chosen

Method	| Path	| Description	
------------- | ------------------------- | ------------- |
GET	| /plannings?from={fromCity}&to={destinyCity}&criteria={TIME/CONNECTION} | Return the shortest itinerary between the cities 

#### Eureka Server
Service registry which has the information about all services, such as IP address and port

#### Zuul Server
API gateway responsible for dynamic routing requests

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

0. [Install Java version 8](https://java.com/en/download/)
0. [Install Docker](https://www.docker.com/community-edition#/download)

### Building applications

0. Go to the project root directory ```adidas-challenge```
0. Execute:
```
$ cd ./eureka-server&&./gradlew build&&cd ..
$ cd ./zuul-server&&./gradlew build&&cd ..
$ cd ./manage-itinerary&&./gradlew build&&cd ..
$ cd ./plan-itinerary&&./gradlew build&&cd ..
```

### Building Docker images

0. Go to the project root directory ```adidas-challenge```
0. Execute:
```
$ docker-compose build --no-cache
```

### Running applications

0. Go to the project root directory ```adidas-challenge```
0. Execute:
```
$ docker-compose up -d
```

### Request examples

View all itineraries
```
http://localhost:9009/itinerary/itineraries

[
    {
        "id": 4,
        "departureCity": "Paris",
        "arrivalCity": "Brussels",
        "departureTime": "2019-10-14T07:25:00",
        "arrivalTime": "2019-10-14T08:25:00"
    },
    {
        "id": 3,
        "departureCity": "Brussels",
        "arrivalCity": "Madrid",
        "departureTime": "2019-10-14T14:35:00",
        "arrivalTime": "2019-10-14T16:50:00"
    },
    {
        "id": 9,
        "departureCity": "Madrid",
        "arrivalCity": "Berlin",
        "departureTime": "2019-10-14T12:20:00",
        "arrivalTime": "2019-10-14T15:25:00"
    }
]
```

Shortest way in Connections
```
http://localhost:9009/planning/plannings?from=Paris&to=Berlin&criteria=CONNECTIONS

[
    {
        "id": 4,
        "departureCity": "Paris",
        "arrivalCity": "Brussels",
        "departureTime": "2019-10-14T07:25:00",
        "arrivalTime": "2019-10-14T08:25:00"
    },
    {
        "id": 3,
        "departureCity": "Brussels",
        "arrivalCity": "Madrid",
        "departureTime": "2019-10-14T14:35:00",
        "arrivalTime": "2019-10-14T16:50:00"
    },
    {
        "id": 9,
        "departureCity": "Madrid",
        "arrivalCity": "Berlin",
        "departureTime": "2019-10-14T12:20:00",
        "arrivalTime": "2019-10-14T15:25:00"
    }
]
```

Shortest way in Time
```
http://localhost:9009/planning/plannings?from=Paris&to=Madrid&criteria=TIME

[
    {
        "id": 4,
        "departureCity": "Paris",
        "arrivalCity": "Brussels",
        "departureTime": "2019-10-14T07:25:00",
        "arrivalTime": "2019-10-14T08:25:00"
    },
    {
        "id": 3,
        "departureCity": "Brussels",
        "arrivalCity": "Madrid",
        "departureTime": "2019-10-14T14:35:00",
        "arrivalTime": "2019-10-14T16:50:00"
    }
]
```

### Stopping applications

0. Go to the project root directory ```adidas-challenge```
0. Execute:
```
$ docker-compose stop
```

### Scaling applications

0. Go to the project root directory ```adidas-challenge```
0. Execute:
```
$ docker-compose stop
$ docker-compose up -d
$ docker-compose scale itinerary=2
$ docker-compose scale planning=2
```

## API Documentation

0. Go to a browser and type: <http://localhost:8762/swagger-ui.html>

## Pipeline proposal

0. Go to <https://github.com/vinisol/adidas-challenge/blob/master/pipeline_proposal.pptx>

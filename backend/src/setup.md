# Robot Delivery System Backend — Setup 

## Overview
Spring Boot backend for a robot delivery system with 
MySQL database and Q-learning integration. The system supports robot registration, 
sensor data ingestion, and delivery lifecycle management with real-time console
logging.

## Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+
- Python 3.8+
- pip

## Database Setup
Create the database schema:
```bash
mysql -u root -p < database_schema.sql
```

# Application Config
Update `application.yaml` with your *MYSQL* login
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:8080/robot_delivery
    username: root
    password: YOUR_MYSQL_PASSWORD
```

# Building Backend
```bash
cd robot-delivery-backend
mvn clean install
mvn spring-boot:run
```

> Note: If there are any changes to the code, you must kill **all** processes and rerun `mvn clean install`
> >And if you are rerunning the code on the same *sql database*, change the values OR go in the sql shell and drop the tables

# API
To use the api, there is 3 steps:
1. Register the robot with id and name
2. Send sample sensor test info
3. Demo a delivery 

**Register**
```bash
curl -X POST http://localhost:8080/api/robots/register \
  -H "Content-Type: application/json" \
  -d '{"robotId":"ROBOT001","name":"Test"}'
```

**Sensor**
```bash
curl -X POST http://localhost:8080/api/robots/data \
  -H "Content-Type: application/json" \
  -d '{
    "robotId":"ROBOT001",
    "position_x":2,
    "position_y":3,
    "leftObstacle":false,
    "frontObstacle":false,
    "rightObstacle":true,
    "batteryLevel":95
  }'
```

**Delivery**
```bash
curl -X POST http://localhost:8080/api/deliveries \
  -H "Content-Type: application/json" \
  -d '{"robotId":"ROBOT001","destinationX":4,"destinationY":4}'
```

## API Endpoints

### Robots
- `POST /api/robots/register`
- `POST /api/robots/data`
- `GET /api/robots`
- `GET /api/robots/{robotId}`
- `GET /api/robots/{robotId}/sensors`

### Deliveries
- `POST /api/deliveries`
- `POST /api/deliveries/{id}/start`
- `POST /api/deliveries/{id}/complete`
- `GET /api/deliveries`
- `GET /api/deliveries/stats`

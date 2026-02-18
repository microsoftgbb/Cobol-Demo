# COBOL AS400 to Java Microservices Migration

This directory contains Java microservices that have been migrated from COBOL AS400 legacy applications.

## Overview

Two COBOL AS400 applications have been migrated to modern Java Spring Boot microservices:

1. **Canada Day Calculator Service** - Migrated from `CANDAY01.CBLLE`
2. **Application Logging Service** - Migrated from `LOG0010CB.cblle`

## Architecture

### Holiday Service (Canada Day Calculator)
- **Port**: 8080
- **Technology**: Spring Boot 3.2.0, Java 17
- **Original COBOL**: `AS400/COBOL_examples/Holidays/QCBLLESRC/CANDAY01.CBLLE`
- **Functionality**: Calculates what day of the week Canada Day (July 1st) falls on for any given year

### Logging Service
- **Port**: 8081
- **Technology**: Spring Boot 3.2.0, Java 17, JPA, H2 Database
- **Original COBOL**: `AS400/COBOL_examples/Logging/QCBLLESRC/LOG0010CB.cblle`
- **Functionality**: Centralized logging service for application events with job context tracking

## Migration Details

### COBOL to Java Mapping

#### Canada Day Calculator
```
COBOL CANDAY01                      Java CanadaDayService
├── WS-INPUT-YEAR                  ├── int year (parameter)
├── WS-DAY-OF-WEEK                 ├── DayOfWeek (Java time API)
├── WS-DAY-NAME                    ├── String dayName
├── FUNCTION INTEGER-OF-DATE       ├── LocalDate.of(year, JULY, 1)
└── FUNCTION MOD                   └── getDayOfWeek()
```

#### Application Logging
```
COBOL LOG0010CB                     Java LoggingService
├── msgtext (40 chars)             ├── String messageText
├── xdate (8 chars YYYYMMDD)       ├── LocalDate logDate
├── xtime (8 chars)                ├── LocalTime logTime
├── xjob (10 chars)                ├── String jobName
├── xuser (10 chars)               ├── String userName
├── xjobnum (6 chars)              ├── String jobNumber
└── LOGP0 physical file            └── JPA Entity (H2 database)
```

## Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- Docker and Docker Compose (for containerized deployment)

### Option 1: Run with Maven

#### Holiday Service
```bash
cd holiday-service/canadaday-calculator
mvn spring-boot:run
```

#### Logging Service
```bash
cd logging-service/application-logger
mvn spring-boot:run
```

### Option 2: Run with Docker Compose
```bash
cd java-services
docker-compose up --build
```

This will start both services:
- Holiday Service: http://localhost:8080
- Logging Service: http://localhost:8081

### Option 3: Build and Run JARs
```bash
# Build Holiday Service
cd holiday-service/canadaday-calculator
mvn clean package
java -jar target/canadaday-calculator-1.0.0.jar

# Build Logging Service
cd logging-service/application-logger
mvn clean package
java -jar target/application-logger-1.0.0.jar
```

## API Documentation

### Canada Day Calculator Service

#### Calculate Canada Day
```http
GET /api/v1/canada-day/{year}
```

**Parameters:**
- `year` (path parameter): Year to calculate (1600-3000)

**Example Request:**
```bash
curl http://localhost:8080/api/v1/canada-day/2024
```

**Example Response:**
```json
{
  "year": 2024,
  "dayOfWeek": "Monday",
  "message": "Canada Day (July 1, 2024) falls on a Monday. Canada Day is on a weekday - enjoy the long weekend! Great way to start the week with a holiday!",
  "weekend": false
}
```

#### Health Check
```http
GET /api/v1/canada-day/health
```

**Example Response:**
```json
{
  "status": "UP",
  "service": "Canada Day Calculator",
  "migrated_from": "COBOL AS400 CANDAY01.CBLLE"
}
```

### Application Logging Service

#### Create Log Entry
```http
POST /api/v1/logs
Content-Type: application/json
```

**Request Body:**
```json
{
  "messageText": "User login successful",
  "userName": "JOHN",
  "jobName": "WEBAPP",
  "jobNumber": "123456"
}
```

**Example Request:**
```bash
curl -X POST http://localhost:8081/api/v1/logs \
  -H "Content-Type: application/json" \
  -d '{
    "messageText": "User login successful",
    "userName": "JOHN",
    "jobName": "WEBAPP",
    "jobNumber": "123456"
  }'
```

**Example Response:**
```json
{
  "id": 1,
  "logDate": "2024-07-01",
  "logTime": "14:30:45",
  "jobName": "WEBAPP",
  "userName": "JOHN",
  "jobNumber": "123456",
  "messageText": "User login successful"
}
```

#### Get All Logs
```http
GET /api/v1/logs
```

#### Get Logs by Date
```http
GET /api/v1/logs/date/{date}
```

**Example:**
```bash
curl http://localhost:8081/api/v1/logs/date/2024-07-01
```

#### Get Logs by User
```http
GET /api/v1/logs/user/{userName}
```

**Example:**
```bash
curl http://localhost:8081/api/v1/logs/user/JOHN
```

#### Get Logs by Job
```http
GET /api/v1/logs/job/{jobName}
```

**Example:**
```bash
curl http://localhost:8081/api/v1/logs/job/WEBAPP
```

#### Health Check
```http
GET /api/v1/logs/health
```

## Testing the Services

### Test Holiday Service
```bash
# Test valid year
curl http://localhost:8080/api/v1/canada-day/2025

# Test another year
curl http://localhost:8080/api/v1/canada-day/1867

# Test invalid year (should return error)
curl http://localhost:8080/api/v1/canada-day/3500
```

### Test Logging Service
```bash
# Create a log entry
curl -X POST http://localhost:8081/api/v1/logs \
  -H "Content-Type: application/json" \
  -d '{"messageText":"System startup","userName":"ADMIN","jobName":"INIT","jobNumber":"000001"}'

# Get all logs
curl http://localhost:8081/api/v1/logs

# Get today's logs
curl http://localhost:8081/api/v1/logs/date/$(date +%Y-%m-%d)
```

## Actuator Endpoints

Both services expose Spring Boot Actuator endpoints for monitoring:

- Health: `http://localhost:8080/actuator/health` (Holiday Service)
- Health: `http://localhost:8081/actuator/health` (Logging Service)
- Info: `http://localhost:8080/actuator/info` (Holiday Service)
- Info: `http://localhost:8081/actuator/info` (Logging Service)

## Development

### Project Structure

```
java-services/
├── docker-compose.yml
├── holiday-service/
│   └── canadaday-calculator/
│       ├── pom.xml
│       ├── Dockerfile
│       └── src/
│           ├── main/
│           │   ├── java/com/contoso/holiday/
│           │   │   ├── CanadaDayCalculatorApplication.java
│           │   │   ├── controller/
│           │   │   │   └── CanadaDayController.java
│           │   │   ├── service/
│           │   │   │   └── CanadaDayService.java
│           │   │   └── model/
│           │   │       └── CanadaDayResponse.java
│           │   └── resources/
│           │       └── application.properties
│           └── test/
└── logging-service/
    └── application-logger/
        ├── pom.xml
        ├── Dockerfile
        └── src/
            ├── main/
            │   ├── java/com/contoso/logging/
            │   │   ├── ApplicationLoggerService.java
            │   │   ├── controller/
            │   │   │   └── LoggingController.java
            │   │   ├── service/
            │   │   │   └── LoggingService.java
            │   │   ├── model/
            │   │   │   ├── LogRecord.java
            │   │   │   └── LogRequest.java
            │   │   └── repository/
            │   │       └── LogRecordRepository.java
            │   └── resources/
            │       └── application.properties
            └── test/
```

### Building the Services

```bash
# Build Holiday Service
cd holiday-service/canadaday-calculator
mvn clean install

# Build Logging Service
cd logging-service/application-logger
mvn clean install
```

### Running Tests

```bash
# Run Holiday Service tests
cd holiday-service/canadaday-calculator
mvn test

# Run Logging Service tests
cd logging-service/application-logger
mvn test
```

## Migration Notes

### Key Differences from COBOL

1. **Date Handling**: Java's `LocalDate` API is used instead of COBOL's `FUNCTION INTEGER-OF-DATE`
2. **Data Storage**: Logging service uses JPA with H2 database instead of physical files (LOGP0.pf)
3. **Field Lengths**: COBOL fixed-length fields are validated but stored as VARCHAR in the database
4. **Job Context**: In COBOL, job information was retrieved via CL program `getjoba1cl`. In Java, this is passed as request parameters with defaults
5. **Error Handling**: Java uses exception handling instead of COBOL's file status codes

### Preserved Behavior

- Year validation range (1600-3000) matches original COBOL
- Message text length limit (40 characters) matches COBOL LOGP0 field
- Job name (10 chars), user name (10 chars), job number (6 chars) field lengths preserved
- Day-of-week calculation produces identical results to COBOL

## Deployment

### Docker Deployment

1. Build and start services:
   ```bash
   docker-compose up -d
   ```

2. View logs:
   ```bash
   docker-compose logs -f
   ```

3. Stop services:
   ```bash
   docker-compose down
   ```

### Production Considerations

1. **Database**: Replace H2 in-memory database with a production database (PostgreSQL, MySQL, etc.)
2. **Configuration**: Use environment variables or external configuration for production settings
3. **Monitoring**: Integrate with monitoring tools (Prometheus, Grafana, ELK stack)
4. **Security**: Add authentication/authorization (Spring Security with OAuth2/JWT)
5. **API Gateway**: Consider adding an API Gateway (Spring Cloud Gateway, Kong, etc.)
6. **Service Discovery**: For multiple instances, add service discovery (Eureka, Consul)

## Troubleshooting

### Port Already in Use
If ports 8080 or 8081 are already in use, modify the `application.properties` files:

```properties
# Holiday Service
server.port=9080

# Logging Service
server.port=9081
```

### Maven Build Failures
Ensure Java 17 is being used:
```bash
java -version
mvn -version
```

### Docker Issues
Check Docker is running:
```bash
docker ps
docker-compose ps
```

## References

- Original COBOL Programs:
  - `AS400/COBOL_examples/Holidays/QCBLLESRC/CANDAY01.CBLLE`
  - `AS400/COBOL_examples/Logging/QCBLLESRC/LOG0010CB.cblle`
- Documentation:
  - `docs/PRD-Canada-Day-Calculator.md`
  - `docs/Technical-Specification-Canada-Day-Calculator.md`
- Spring Boot Documentation: https://spring.io/projects/spring-boot
- Spring Data JPA: https://spring.io/projects/spring-data-jpa

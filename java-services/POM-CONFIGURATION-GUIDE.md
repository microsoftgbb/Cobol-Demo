# POM Configuration Guide for Java Microservices

## Overview

The Maven POM files for both microservices require Spring Boot parent configuration and specific dependencies. Since direct POM content cannot be provided, this guide describes the required configuration.

## Holiday Service POM Configuration

### Location
`java-services/holiday-service/canadaday-calculator/pom.xml`

### Required Elements

1. **Parent Configuration**:
   - Use Spring Boot Starter Parent version 3.2.0
   - Group ID: org.springframework.boot
   - Artifact ID: spring-boot-starter-parent

2. **Project Coordinates**:
   - Group ID: com.contoso.holiday
   - Artifact ID: canadaday-calculator
   - Version: 1.0.0
   - Packaging: jar

3. **Properties**:
   - Java version: 17
   - Project encoding: UTF-8

4. **Required Dependencies**:
   - spring-boot-starter-web (for REST API)
   - spring-boot-starter-validation (for request validation)
   - spring-boot-starter-actuator (for health endpoints)
   - spring-boot-starter-test (scope: test)

5. **Build Configuration**:
   - Spring Boot Maven Plugin
   - Maven Compiler Plugin with Java 17

## Logging Service POM Configuration

### Location
`java-services/logging-service/application-logger/pom.xml`

### Required Elements

1. **Parent Configuration**:
   - Use Spring Boot Starter Parent version 3.2.0
   - Group ID: org.springframework.boot
   - Artifact ID: spring-boot-starter-parent

2. **Project Coordinates**:
   - Group ID: com.contoso.logging
   - Artifact ID: application-logger
   - Version: 1.0.0
   - Packaging: jar

3. **Properties**:
   - Java version: 17
   - Project encoding: UTF-8

4. **Required Dependencies**:
   - spring-boot-starter-web (for REST API)
   - spring-boot-starter-data-jpa (for database access)
   - spring-boot-starter-validation (for request validation)
   - spring-boot-starter-actuator (for health endpoints)
   - h2 database driver (scope: runtime)
   - spring-boot-starter-test (scope: test)

5. **Build Configuration**:
   - Spring Boot Maven Plugin
   - Maven Compiler Plugin with Java 17

## Manual POM Update Instructions

If the generated POM files don't have Spring Boot configuration:

### Step 1: Update Holiday Service POM
```bash
cd java-services/holiday-service/canadaday-calculator
# Edit pom.xml to include Spring Boot parent and dependencies listed above
```

### Step 2: Update Logging Service POM
```bash
cd java-services/logging-service/application-logger
# Edit pom.xml to include Spring Boot parent and dependencies listed above
```

### Step 3: Verify Configuration
```bash
# Test Holiday Service
cd java-services/holiday-service/canadaday-calculator
mvn clean compile

# Test Logging Service  
cd java-services/logging-service/application-logger
mvn clean compile
```

## Dependency Security Status

All dependencies have been verified against GitHub Advisory Database:
- ✅ spring-boot-starter-web 3.2.0 - No vulnerabilities
- ✅ spring-boot-starter-data-jpa 3.2.0 - No vulnerabilities
- ✅ h2 database 2.2.224 - No vulnerabilities

## Alternative: Use Spring Initializr

If POM configuration is problematic, you can regenerate the projects using Spring Initializr:

### For Holiday Service:
1. Visit start.spring.io
2. Project: Maven
3. Language: Java
4. Spring Boot: 3.2.0
5. Group: com.contoso.holiday
6. Artifact: canadaday-calculator
7. Java: 17
8. Dependencies: Spring Web, Validation, Actuator
9. Generate and replace the project

### For Logging Service:
1. Visit start.spring.io
2. Project: Maven
3. Language: Java
4. Spring Boot: 3.2.0
5. Group: com.contoso.logging
6. Artifact: application-logger
7. Java: 17
8. Dependencies: Spring Web, Spring Data JPA, Validation, Actuator, H2 Database
9. Generate and replace the project

After regeneration, copy the Java source files from the existing services into the new project structure.

## Troubleshooting

### Issue: Compilation errors about missing Spring classes
**Solution**: Ensure Spring Boot parent is properly configured in POM

### Issue: Cannot resolve dependencies
**Solution**: Run `mvn clean install -U` to force update dependencies

### Issue: Wrong Java version
**Solution**: Verify Java 17 is installed with `java -version` and `mvn -version`

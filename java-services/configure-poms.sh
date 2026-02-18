#!/bin/bash

# Script to configure Maven POM files for the microservices

echo "Configuring Holiday Service POM..."
cd /home/runner/work/Cobol-Demo/Cobol-Demo/java-services/holiday-service/canadaday-calculator

# Backup original
cp pom.xml pom.xml.bak

# Create new POM with proper Spring Boot configuration
{
  echo '<?xml version="1.0" encoding="UTF-8"?>'
  echo '<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"'
  echo '  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">'
  echo '  <modelVersion>4.0.0</modelVersion>'
  echo '  <parent><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-parent</artifactId><version>3.2.0</version></parent>'
  echo '  <groupId>com.contoso.holiday</groupId>'
  echo '  <artifactId>canadaday-calculator</artifactId>'
  echo '  <version>1.0.0</version>'
  echo '  <name>Canada Day Calc Service</name>'
  echo '  <properties><java.version>17</java.version></properties>'
  echo '  <dependencies>'
  echo '    <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-web</artifactId></dependency>'
  echo '    <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-validation</artifactId></dependency>'
  echo '    <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-actuator</artifactId></dependency>'
  echo '    <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-test</artifactId><scope>test</scope></dependency>'
  echo '  </dependencies>'
  echo '  <build><plugins><plugin><groupId>org.springframework.boot</groupId><artifactId>spring-boot-maven-plugin</artifactId></plugin></plugins></build>'
  echo '</project>'
} > pom.xml

echo "Holiday Service POM configured"

echo "Configuring Logging Service POM..."
cd /home/runner/work/Cobol-Demo/Cobol-Demo/java-services/logging-service/application-logger

# Backup original
cp pom.xml pom.xml.bak

# Create new POM with proper Spring Boot configuration
{
  echo '<?xml version="1.0" encoding="UTF-8"?>'
  echo '<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"'
  echo '  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">'
  echo '  <modelVersion>4.0.0</modelVersion>'
  echo '  <parent><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-parent</artifactId><version>3.2.0</version></parent>'
  echo '  <groupId>com.contoso.logging</groupId>'
  echo '  <artifactId>application-logger</artifactId>'
  echo '  <version>1.0.0</version>'
  echo '  <name>Application Logger Service</name>'
  echo '  <properties><java.version>17</java.version></properties>'
  echo '  <dependencies>'
  echo '    <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-web</artifactId></dependency>'
  echo '    <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-data-jpa</artifactId></dependency>'
  echo '    <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-validation</artifactId></dependency>'
  echo '    <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-actuator</artifactId></dependency>'
  echo '    <dependency><groupId>com.h2database</groupId><artifactId>h2</artifactId><scope>runtime</scope></dependency>'
  echo '    <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-test</artifactId><scope>test</scope></dependency>'
  echo '  </dependencies>'
  echo '  <build><plugins><plugin><groupId>org.springframework.boot</groupId><artifactId>spring-boot-maven-plugin</artifactId></plugin></plugins></build>'
  echo '</project>'
} > pom.xml

echo "Logging Service POM configured"
echo "All POMs updated successfully!"

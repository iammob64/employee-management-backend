package com.javafullstack.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MAIN ENTRY POINT of the Spring Boot application.
 *
 * @SpringBootApplication = 3 annotations combined:
 *   @Configuration       → this class can define Spring beans
 *   @EnableAutoConfiguration → Spring Boot auto-configures everything (DB, web, etc.)
 *   @ComponentScan       → scans this package and sub-packages for @Component, @Service, etc.
 *
 * When you run this class, Spring Boot:
 *  1. Starts an embedded Tomcat server on port 8080
 *  2. Creates the H2 in-memory database
 *  3. Creates the EMPLOYEE table from our Entity class
 *  4. Registers all our REST endpoints
 */
@SpringBootApplication
public class EmployeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
        System.out.println("✅ Employee Management Backend is running!");
        System.out.println("📌 API Base URL : http://localhost:8080/api/employees");
        System.out.println("🗄️  H2 Console   : http://localhost:8080/h2-console");
    }
}

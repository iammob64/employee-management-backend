package com.javafullstack.employee;

import com.javafullstack.employee.model.Employee;
import com.javafullstack.employee.model.Promotion;
import com.javafullstack.employee.repository.EmployeeRepository;
import com.javafullstack.employee.repository.PromotionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DATA INITIALIZER
 *
 * Runs automatically when the Spring Boot app starts.
 * Pre-loads sample employees and promotions (with project descriptions)
 * into the H2 database so the app doesn't start empty.
 *
 * NOTE: Attendance records are NOT seeded here — those are created
 * in real-time by employees marking their own attendance via
 * POST /api/attendance.
 */
@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(
            EmployeeRepository employeeRepository,
            PromotionRepository promotionRepository) {
        return args -> {

            // ── Seed Employees ─────────────────────────────────────────────────
            if (employeeRepository.count() == 0) {
                employeeRepository.save(new Employee(
                        "Rudra", "Singh", "rudra.sharma@javafullstack.com",
                        "Engineering", "Senior Software Engineer", 95000.0));
                employeeRepository.save(new Employee(
                        "AyushMani", "Tripathi", "ayushmani.nair@javafullstack.com",
                        "Engineering", "Java Developer", 80000.0));
                employeeRepository.save(new Employee(
                        "Aditya", "Prakash", "aditya.verma@javafullstack.com",
                        "HR", "HR Manager", 75000.0));
                employeeRepository.save(new Employee(
                        "Punit", "Mohan", "punit.mohan@javafullstack.com",
                        "Finance", "Financial Analyst", 70000.0));
                employeeRepository.save(new Employee(
                        "Ananya", "Singh", "ananya.singh@javafullstack.com",
                        "Engineering", "DevOps Engineer", 88000.0));
                employeeRepository.save(new Employee(
                        "Rahul", "Verma", "rahul.verma@javafullstack.com",
                        "Engineering", "Frontend Developer", 78000.0));

                employeeRepository.save(new Employee(
                        "Sneha", "Kapoor", "sneha.kapoor@javafullstack.com",
                        "HR", "HR Executive", 65000.0));

                employeeRepository.save(new Employee(
                        "Vikas", "Arora", "vikas.arora@javafullstack.com",
                        "Marketing", "Marketing Manager", 82000.0));

                employeeRepository.save(new Employee(
                        "Aman", "Gupta", "aman.gupta@javafullstack.com",
                        "Finance", "Accountant", 72000.0));
                System.out.println("✅ Sample employees loaded into database!");
            }

            // ── Seed Promotions ────────────────────────────────────────────────
            if (promotionRepository.count() == 0) {
                promotionRepository.save(new Promotion(
                        "Arjun Sharma", "Engineering",
                        "Software Engineer", "Senior Software Engineer",
                        "2026-01-15", "₹15,000", "Approved",
                        "ERP System Upgrade (65% complete), Cloud Migration Planning, Microservices architecture redesign for the core platform"));
                promotionRepository.save(new Promotion(
                        "Priya Nair", "Engineering",
                        "Junior Developer", "Java Developer",
                        "2026-03-01", "₹10,000", "Approved",
                        "Employee Onboarding App (80% complete), HR Portal API integration, REST API documentation and OpenAPI spec authoring"));
                promotionRepository.save(new Promotion(
                        "Karthik Rao", "Engineering",
                        "DevOps Associate", "DevOps Engineer",
                        "2026-04-10", "₹12,000", "Approved",
                        "Cloud Migration (10% complete), CI/CD pipeline setup for 4 services, Docker containerization of 6 applications, on-call incident response"));
                promotionRepository.save(new Promotion(
                        "Sneha Patel", "Finance",
                        "Finance Associate", "Financial Analyst",
                        "2026-05-01", "₹8,000", "Pending",
                        "Payroll Automation (40% complete), Monthly financial reporting overhaul, Budget forecasting model, reduced report preparation time by 30%"));
                promotionRepository.save(new Promotion(
                        "Rohit Verma", "HR",
                        "HR Executive", "HR Manager",
                        "2025-11-20", "₹20,000", "Approved",
                        "HR Portal Redesign (completed on time), Employee onboarding process revamp reducing time-to-productivity by 40%, Policy documentation update covering all departments"));
                promotionRepository.save(new Promotion(
                        "Rahul Verma", "Engineering",
                        "Frontend Developer", "Senior Frontend Developer",
                        "2026-06-01", "₹18,000", "Approved",
                        "Admin dashboard redesign, attendance analytics module, React optimization and performance improvements"));
                System.out.println("✅ Sample promotions (with projects) loaded into database!");
            }
        };
    }
}

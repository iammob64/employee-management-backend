package com.javafullstack.employee.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * EMPLOYEE ENTITY
 *
 * This Java class maps directly to a database TABLE called "employees".
 * JPA (Java Persistence API) does this mapping automatically.
 *
 * Each field in this class = a COLUMN in the database.
 *
 * Annotations explained:
 *  @Entity       → tells JPA this is a database entity
 *  @Table        → names the database table
 *  @Id           → marks the primary key field
 *  @GeneratedValue → auto-increments the ID (1, 2, 3, ...)
 *  @Column       → maps field to a specific column name
 *  @NotBlank     → validation: field cannot be empty
 *  @Email        → validation: must be a valid email format
 */
@Entity
@Table(name = "employees")
public class Employee {

    // PRIMARY KEY — auto-generated (1, 2, 3...)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Employee first name — required, cannot be blank
    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    // Employee last name — required, cannot be blank
    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    // Employee email — must be valid email format
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Department (e.g. "Engineering", "HR", "Finance")
    @NotBlank(message = "Department is required")
    @Column(name = "department")
    private String department;

    // Job title (e.g. "Software Engineer", "Manager")
    @NotBlank(message = "Job title is required")
    @Column(name = "job_title")
    private String jobTitle;

    // Salary
    @Column(name = "salary")
    private Double salary;

    // =====================================================
    // CONSTRUCTORS
    // Java needs: no-arg constructor (for JPA) +
    //             all-args constructor (for convenience)
    // =====================================================

    // No-argument constructor (REQUIRED by JPA)
    public Employee() {}

    // All-argument constructor
    public Employee(String firstName, String lastName, String email,
                    String department, String jobTitle, Double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
        this.jobTitle = jobTitle;
        this.salary = salary;
    }

    // =====================================================
    // GETTERS AND SETTERS
    // These let Spring/Jackson convert this class to JSON
    // and read JSON into this class.
    // =====================================================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }

    // toString: useful for logging/debugging
    @Override
    public String toString() {
        return "Employee{id=" + id + ", name=" + firstName + " " + lastName +
               ", email=" + email + ", dept=" + department + "}";
    }
}

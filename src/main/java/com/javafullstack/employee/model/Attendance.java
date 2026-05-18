package com.javafullstack.employee.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * ATTENDANCE ENTITY
 *
 * Maps to the "attendance" table in the database.
 * Each row represents one employee's attendance for one specific date.
 *
 * Employees mark themselves PRESENT or ABSENT.
 * Admins can query all records or filter by employee email / date.
 */
@Entity
@Table(
    name = "attendance",
    uniqueConstraints = {
        // One record per employee per day — prevents duplicate marking
        @UniqueConstraint(columnNames = {"employee_email", "date"})
    }
)
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Email matches the frontend Auth user's email */
    @NotBlank(message = "Employee email is required")
    @Column(name = "employee_email", nullable = false)
    private String employeeEmail;

    /** Display name of the employee (denormalised for easier reporting) */
    @Column(name = "employee_name")
    private String employeeName;

    /** ISO date string: "2026-05-18" */
    @NotBlank(message = "Date is required")
    @Column(name = "date", nullable = false)
    private String date;

    /** Either "PRESENT" or "ABSENT" */
    @NotBlank(message = "Status is required")
    @Column(name = "status", nullable = false)
    private String status;

    // ── Constructors ──────────────────────────────────────────────────────────

    public Attendance() {}

    public Attendance(String employeeEmail, String employeeName, String date, String status) {
        this.employeeEmail = employeeEmail;
        this.employeeName  = employeeName;
        this.date          = date;
        this.status        = status;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public Long   getId()            { return id; }
    public void   setId(Long id)     { this.id = id; }

    public String getEmployeeEmail()                    { return employeeEmail; }
    public void   setEmployeeEmail(String employeeEmail){ this.employeeEmail = employeeEmail; }

    public String getEmployeeName()                     { return employeeName; }
    public void   setEmployeeName(String employeeName)  { this.employeeName  = employeeName; }

    public String getDate()              { return date; }
    public void   setDate(String date)   { this.date   = date; }

    public String getStatus()                { return status; }
    public void   setStatus(String status)   { this.status = status; }

    @Override
    public String toString() {
        return "Attendance{id=" + id + ", email=" + employeeEmail +
               ", date=" + date + ", status=" + status + "}";
    }
}

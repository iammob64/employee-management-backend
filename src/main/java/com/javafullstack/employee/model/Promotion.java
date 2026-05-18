package com.javafullstack.employee.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * PROMOTION ENTITY
 *
 * Maps to the "promotions" table in the database.
 * Each row records a single promotion event for one employee.
 *
 * The "projects" field (TEXT column) captures the work the employee
 * did that justified the promotion — required when adding a new record.
 */
@Entity
@Table(name = "promotions")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Employee's full name */
    @NotBlank(message = "Employee name is required")
    @Column(name = "name", nullable = false)
    private String name;

    /** Department (e.g. "Engineering") */
    @NotBlank(message = "Department is required")
    @Column(name = "department", nullable = false)
    private String department;

    /** Previous job title */
    @NotBlank(message = "Previous role is required")
    @Column(name = "from_role", nullable = false)
    private String fromRole;

    /** New (promoted) job title */
    @NotBlank(message = "New role is required")
    @Column(name = "to_role", nullable = false)
    private String toRole;

    /** Effective date of the promotion, ISO format: "2026-05-18" */
    @Column(name = "date")
    private String date;

    /** Human-readable salary increase, e.g. "₹15,000" */
    @Column(name = "salary_increase")
    private String salaryIncrease;

    /** "Pending" or "Approved" */
    @Column(name = "status")
    private String status;

    /**
     * Projects / work done by the employee that earned this promotion.
     * Stored as TEXT so it can hold multi-line descriptions.
     * This field is shown in the promotions table and is REQUIRED
     * by the frontend when creating a new record.
     */
    @Column(name = "projects", columnDefinition = "TEXT")
    private String projects;

    // ── Constructors ──────────────────────────────────────────────────────────

    public Promotion() {}

    public Promotion(String name, String department, String fromRole, String toRole,
                     String date, String salaryIncrease, String status, String projects) {
        this.name           = name;
        this.department     = department;
        this.fromRole       = fromRole;
        this.toRole         = toRole;
        this.date           = date;
        this.salaryIncrease = salaryIncrease;
        this.status         = status;
        this.projects       = projects;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public Long   getId()            { return id; }
    public void   setId(Long id)     { this.id = id; }

    public String getName()              { return name; }
    public void   setName(String name)   { this.name = name; }

    public String getDepartment()                    { return department; }
    public void   setDepartment(String department)   { this.department = department; }

    public String getFromRole()              { return fromRole; }
    public void   setFromRole(String fromRole){ this.fromRole = fromRole; }

    public String getToRole()                { return toRole; }
    public void   setToRole(String toRole)   { this.toRole = toRole; }

    public String getDate()              { return date; }
    public void   setDate(String date)   { this.date = date; }

    public String getSalaryIncrease()                    { return salaryIncrease; }
    public void   setSalaryIncrease(String salaryIncrease){ this.salaryIncrease = salaryIncrease; }

    public String getStatus()                { return status; }
    public void   setStatus(String status)   { this.status = status; }

    public String getProjects()                { return projects; }
    public void   setProjects(String projects) { this.projects = projects; }

    @Override
    public String toString() {
        return "Promotion{id=" + id + ", name=" + name + ", " + fromRole + " → " + toRole + "}";
    }
}

package com.javafullstack.employee.repository;

import com.javafullstack.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * EMPLOYEE REPOSITORY — Database Access Layer
 *
 * This interface talks to the H2 database.
 *
 * By extending JpaRepository<Employee, Long>, Spring Data JPA
 * AUTOMATICALLY provides these methods WITHOUT you writing any SQL:
 *
 *   findAll()           → SELECT * FROM employees
 *   findById(id)        → SELECT * FROM employees WHERE id = ?
 *   save(employee)      → INSERT INTO employees (...) VALUES (...)
 *                         or UPDATE employees SET ... WHERE id = ?
 *   deleteById(id)      → DELETE FROM employees WHERE id = ?
 *   existsById(id)      → SELECT COUNT(*) FROM employees WHERE id = ?
 *   count()             → SELECT COUNT(*) FROM employees
 *
 * You can also add CUSTOM methods using Spring's "method name" convention.
 * Spring reads the method name and generates the SQL automatically!
 *
 * Generic types explained:
 *   JpaRepository<Employee, Long>
 *                  ↑          ↑
 *            Entity type    ID type (Long)
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Find all employees in a specific department.
     * Spring generates: SELECT * FROM employees WHERE department = ?
     */
    List<Employee> findByDepartment(String department);

    /**
     * Check if an email already exists (for duplicate prevention).
     * Spring generates: SELECT COUNT(*) > 0 FROM employees WHERE email = ?
     */
    boolean existsByEmail(String email);

    /**
     * Find an employee by email.
     * Spring generates: SELECT * FROM employees WHERE email = ?
     */
    Optional<Employee> findByEmail(String email);

    /**
     * Search employees whose first or last name contains a keyword.
     * Spring generates: SELECT * FROM employees
     *                   WHERE first_name LIKE %?% OR last_name LIKE %?%
     * (IgnoreCase = case-insensitive search)
     */
    List<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
        String firstName, String lastName
    );
}

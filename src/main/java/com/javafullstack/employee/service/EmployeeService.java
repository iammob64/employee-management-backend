package com.javafullstack.employee.service;

import com.javafullstack.employee.model.Employee;
import com.javafullstack.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * EMPLOYEE SERVICE — Business Logic Layer
 *
 * The Service layer sits between the Controller and Repository.
 * It contains BUSINESS RULES — logic that isn't about HTTP or databases,
 * but about how your application should behave.
 *
 * Examples of business logic here:
 *  - Don't allow duplicate email addresses
 *  - Only update the fields that were provided
 *  - Throw meaningful errors when something goes wrong
 *
 * @Service → tells Spring this is a service component.
 *            Spring creates one instance and reuses it everywhere.
 *
 * @Autowired → Spring automatically "injects" (provides) the
 *              EmployeeRepository instance. You don't need to do
 *              new EmployeeRepository() manually.
 */
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    // Constructor injection (preferred over @Autowired on field)
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // =====================================================
    // GET ALL EMPLOYEES
    // =====================================================
    /**
     * Returns a list of all employees in the database.
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // =====================================================
    // GET EMPLOYEE BY ID
    // =====================================================
    /**
     * Returns an employee by their ID.
     * Throws an exception if the employee doesn't exist.
     */
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    // =====================================================
    // CREATE EMPLOYEE
    // =====================================================
    /**
     * Saves a new employee to the database.
     *
     * Business rule: email must be unique.
     * If an employee with that email already exists, throw an error.
     */
    public Employee createEmployee(Employee employee) {
        // Business rule: check for duplicate email
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new RuntimeException(
                "An employee with email '" + employee.getEmail() + "' already exists."
            );
        }
        // Save and return the saved employee (which now has an ID)
        return employeeRepository.save(employee);
    }

    // =====================================================
    // UPDATE EMPLOYEE
    // =====================================================
    /**
     * Updates an existing employee's details.
     *
     * Steps:
     * 1. Find the existing employee (throws if not found)
     * 2. Update only the fields provided
     * 3. Save and return the updated record
     */
    public Employee updateEmployee(Long id, Employee updatedData) {
        // Step 1: Find the existing employee
        Employee existingEmployee = getEmployeeById(id);

        // Step 2: Update each field
        // (We check for null so partial updates work too)
        if (updatedData.getFirstName() != null) {
            existingEmployee.setFirstName(updatedData.getFirstName());
        }
        if (updatedData.getLastName() != null) {
            existingEmployee.setLastName(updatedData.getLastName());
        }
        if (updatedData.getEmail() != null) {
            // If email is changing, make sure the new email isn't taken
            boolean emailTakenByOther = employeeRepository.findByEmail(updatedData.getEmail())
                .map(e -> !e.getId().equals(id))  // true if found AND it's a different person
                .orElse(false);
            if (emailTakenByOther) {
                throw new RuntimeException("Email is already used by another employee.");
            }
            existingEmployee.setEmail(updatedData.getEmail());
        }
        if (updatedData.getDepartment() != null) {
            existingEmployee.setDepartment(updatedData.getDepartment());
        }
        if (updatedData.getJobTitle() != null) {
            existingEmployee.setJobTitle(updatedData.getJobTitle());
        }
        if (updatedData.getSalary() != null) {
            existingEmployee.setSalary(updatedData.getSalary());
        }

        // Step 3: Save and return
        return employeeRepository.save(existingEmployee);
    }

    // =====================================================
    // DELETE EMPLOYEE
    // =====================================================
    /**
     * Deletes an employee by ID.
     * Throws an exception if the employee doesn't exist.
     */
    public void deleteEmployee(Long id) {
        // Make sure they exist first
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    // =====================================================
    // SEARCH EMPLOYEES
    // =====================================================
    /**
     * Searches employees by name keyword (case-insensitive).
     */
    public List<Employee> searchEmployees(String keyword) {
        return employeeRepository
            .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                keyword, keyword
            );
    }

    // =====================================================
    // GET BY DEPARTMENT
    // =====================================================
    /**
     * Returns all employees in a given department.
     */
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }
}

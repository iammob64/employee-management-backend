package com.javafullstack.employee.controller;

import com.javafullstack.employee.model.Employee;
import com.javafullstack.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EMPLOYEE CONTROLLER — The HTTP API Layer
 *
 * This class handles incoming HTTP requests from the browser / React app.
 * Each method here is mapped to a specific HTTP method + URL path.
 *
 * @RestController → combines @Controller + @ResponseBody.
 *   Means: this class handles HTTP requests AND automatically converts
 *   return values to JSON.
 *
 * @RequestMapping("/api/employees") → all URLs in this class start with
 *   http://localhost:8080/api/employees
 *
 * @CrossOrigin → allows React (port 3000) to call this API (port 8080).
 *   Without this, browsers block cross-origin requests (CORS policy).
 *
 * HTTP Methods:
 *   GET    → read data
 *   POST   → create new data
 *   PUT    → update existing data
 *   DELETE → remove data
 *
 * ResponseEntity<T> → lets us control the HTTP status code we send back.
 *   200 OK, 201 Created, 404 Not Found, 400 Bad Request, etc.
 */
@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // =====================================================
    // GET ALL EMPLOYEES
    // URL:    GET /api/employees
    // Returns: JSON array of all employees
    // =====================================================
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees); // 200 OK
    }

    // =====================================================
    // GET EMPLOYEE BY ID
    // URL:    GET /api/employees/{id}  e.g. /api/employees/1
    // Returns: JSON of single employee
    // =====================================================
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(employee); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse(e.getMessage())); // 404 Not Found
        }
    }

    // =====================================================
    // CREATE NEW EMPLOYEE
    // URL:    POST /api/employees
    // Body:   JSON with employee details
    // Returns: JSON of created employee (with its new ID)
    // =====================================================
    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee employee) {
        // @Valid        → runs the @NotBlank, @Email etc. validations
        // @RequestBody  → reads JSON from request body and converts to Employee object
        try {
            Employee created = employeeService.createEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(created); // 201 Created
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse(e.getMessage())); // 400 Bad Request
        }
    }

    // =====================================================
    // UPDATE EMPLOYEE
    // URL:    PUT /api/employees/{id}
    // Body:   JSON with fields to update
    // Returns: JSON of updated employee
    // =====================================================
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employeeDetails) {
        try {
            Employee updated = employeeService.updateEmployee(id, employeeDetails);
            return ResponseEntity.ok(updated); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse(e.getMessage()));
        }
    }

    // =====================================================
    // DELETE EMPLOYEE
    // URL:    DELETE /api/employees/{id}
    // Returns: success message
    // =====================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Employee deleted successfully");
            return ResponseEntity.ok(response); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse(e.getMessage())); // 404 Not Found
        }
    }

    // =====================================================
    // SEARCH EMPLOYEES BY NAME
    // URL:    GET /api/employees/search?keyword=John
    // Returns: JSON array of matching employees
    // =====================================================
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(
            @RequestParam String keyword) {
        List<Employee> results = employeeService.searchEmployees(keyword);
        return ResponseEntity.ok(results);
    }

    // =====================================================
    // GET EMPLOYEES BY DEPARTMENT
    // URL:    GET /api/employees/department/{dept}
    // Returns: JSON array of employees in that department
    // =====================================================
    @GetMapping("/department/{department}")
    public ResponseEntity<List<Employee>> getByDepartment(
            @PathVariable String department) {
        List<Employee> employees = employeeService.getEmployeesByDepartment(department);
        return ResponseEntity.ok(employees);
    }

    // =====================================================
    // HELPER: Standard error response format
    // =====================================================
    private Map<String, String> errorResponse(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        return error;
    }
}

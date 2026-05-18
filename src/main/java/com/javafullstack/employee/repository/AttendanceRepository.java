package com.javafullstack.employee.repository;

import com.javafullstack.employee.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ATTENDANCE REPOSITORY
 *
 * Spring Data JPA auto-generates SQL from these method names:
 *
 *   findAll()                              → SELECT * FROM attendance
 *   findByEmployeeEmail(email)             → WHERE employee_email = ?
 *   findByDate(date)                       → WHERE date = ?
 *   findByEmployeeEmailAndDate(email,date) → WHERE employee_email = ? AND date = ?
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    /** All records for a specific employee (their own history view) */
    List<Attendance> findByEmployeeEmailOrderByDateDesc(String employeeEmail);

    /** All records for a given date (admin daily view) */
    List<Attendance> findByDate(String date);

    /**
     * Look up a specific employee's record for a specific date.
     * Used to check if they already marked attendance today.
     */
    Optional<Attendance> findByEmployeeEmailAndDate(String employeeEmail, String date);
}

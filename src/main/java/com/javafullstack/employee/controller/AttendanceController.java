package com.javafullstack.employee.controller;

import com.javafullstack.employee.model.Attendance;
import com.javafullstack.employee.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * ATTENDANCE CONTROLLER — REST API
 *
 * Base URL: /api/attendance
 *
 * Endpoints:
 *
 *  POST   /api/attendance
 *      Mark or update attendance.
 *      Body: { employeeEmail, employeeName, date, status }
 *      → Creates if new, updates if already marked that day.
 *
 *  GET    /api/attendance
 *      Returns ALL attendance records (admin view).
 *
 *  GET    /api/attendance/employee/{email}
 *      Returns all records for one employee (their own history).
 *
 *  GET    /api/attendance/today/{email}
 *      Returns today's record for that employee, or 404 if not yet marked.
 *
 *  GET    /api/attendance/date/{date}
 *      Returns all records for a specific date (admin daily view).
 *      Date format: "2026-05-18"
 */
@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "http://localhost:3000")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // ── Mark attendance ────────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<?> markAttendance(@Valid @RequestBody Attendance attendance) {
        try {
            Attendance saved = attendanceService.markAttendance(attendance);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse(e.getMessage()));
        }
    }

    // ── Get all (admin) ────────────────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<List<Attendance>> getAllAttendance() {
        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }

    // ── Get by employee email ──────────────────────────────────────────────────

    @GetMapping("/employee/{email}")
    public ResponseEntity<List<Attendance>> getByEmployee(@PathVariable String email) {
        return ResponseEntity.ok(attendanceService.getAttendanceByEmployee(email));
    }

    // ── Check today's record ───────────────────────────────────────────────────

    @GetMapping("/today/{email}")
    public ResponseEntity<?> getTodayRecord(@PathVariable String email) {
        Optional<Attendance> record = attendanceService.getTodayRecord(email);
        if (record.isPresent()) {
            return ResponseEntity.ok(record.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(errorResponse("No attendance record found for today."));
    }

    // ── Get by date (admin daily view) ────────────────────────────────────────

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Attendance>> getByDate(@PathVariable String date) {
        return ResponseEntity.ok(attendanceService.getAttendanceByDate(date));
    }

    // ── Helper ─────────────────────────────────────────────────────────────────

    private Map<String, String> errorResponse(String message) {
        Map<String, String> err = new HashMap<>();
        err.put("error", message);
        return err;
    }
}

package com.javafullstack.employee.service;

import com.javafullstack.employee.model.Attendance;
import com.javafullstack.employee.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * ATTENDANCE SERVICE — Business Logic
 *
 * Rules enforced here:
 *  1. An employee can only have ONE attendance record per day.
 *     If they call markAttendance again for the same date, the existing
 *     record is UPDATED (so they can correct a mistake).
 *  2. Status must be "PRESENT" or "ABSENT" — anything else is rejected.
 *  3. The date defaults to today if not supplied.
 */
@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    // ── Get all (admin) ────────────────────────────────────────────────────────

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    // ── Get by employee email ──────────────────────────────────────────────────

    public List<Attendance> getAttendanceByEmployee(String email) {
        return attendanceRepository.findByEmployeeEmailOrderByDateDesc(email);
    }

    // ── Get by date (admin daily view) ────────────────────────────────────────

    public List<Attendance> getAttendanceByDate(String date) {
        return attendanceRepository.findByDate(date);
    }

    // ── Check today's record for an employee ──────────────────────────────────

    public Optional<Attendance> getTodayRecord(String email) {
        String today = LocalDate.now().toString(); // "2026-05-18"
        return attendanceRepository.findByEmployeeEmailAndDate(email, today);
    }

    // ── Mark attendance (create or update) ────────────────────────────────────

    /**
     * Marks an employee's attendance.
     * If a record for (email, date) already exists, updates its status.
     * Otherwise, creates a new record.
     *
     * Business rules:
     *  - Status must be "PRESENT" or "ABSENT".
     *  - Date defaults to today if blank.
     */
    public Attendance markAttendance(Attendance request) {
        // Validate status
        String status = request.getStatus();
        if (!"PRESENT".equalsIgnoreCase(status) && !"ABSENT".equalsIgnoreCase(status)) {
            throw new RuntimeException("Status must be PRESENT or ABSENT, got: " + status);
        }
        request.setStatus(status.toUpperCase());

        // Default date to today
        if (request.getDate() == null || request.getDate().isBlank()) {
            request.setDate(LocalDate.now().toString());
        }

        // Check for existing record on that date
        Optional<Attendance> existing = attendanceRepository
            .findByEmployeeEmailAndDate(request.getEmployeeEmail(), request.getDate());

        if (existing.isPresent()) {
            // Update the existing record
            Attendance record = existing.get();
            record.setStatus(request.getStatus());
            if (request.getEmployeeName() != null) {
                record.setEmployeeName(request.getEmployeeName());
            }
            return attendanceRepository.save(record);
        }

        // Create new record
        return attendanceRepository.save(request);
    }
}

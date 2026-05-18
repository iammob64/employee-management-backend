package com.javafullstack.employee.controller;

import com.javafullstack.employee.model.Promotion;
import com.javafullstack.employee.service.PromotionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PROMOTION CONTROLLER — REST API
 *
 * Base URL: /api/promotions
 *
 * Endpoints:
 *
 *  GET    /api/promotions
 *      Returns all promotion records.
 *
 *  GET    /api/promotions/{id}
 *      Returns a single promotion by ID.
 *
 *  GET    /api/promotions/employee/{name}
 *      Returns all promotions for an employee (by name).
 *
 *  GET    /api/promotions/status/{status}
 *      Returns promotions filtered by status ("Pending" / "Approved").
 *
 *  POST   /api/promotions
 *      Creates a new promotion.
 *      Body: { name, department, fromRole, toRole, date, salaryIncrease, projects }
 *      NOTE: "projects" is required — request is rejected if it's blank.
 *
 *  PATCH  /api/promotions/{id}/status
 *      Updates the status of a promotion (e.g. Admin approves a Pending record).
 *      Body: { "status": "Approved" }
 */
@RestController
@RequestMapping("/api/promotions")
@CrossOrigin(origins = "http://localhost:3000")
public class PromotionController {

    private final PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    // ── Get all ───────────────────────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<List<Promotion>> getAllPromotions() {
        return ResponseEntity.ok(promotionService.getAllPromotions());
    }

    // ── Get by ID ─────────────────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(promotionService.getPromotionById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse(e.getMessage()));
        }
    }

    // ── Get by employee name ──────────────────────────────────────────────────

    @GetMapping("/employee/{name}")
    public ResponseEntity<List<Promotion>> getByEmployee(@PathVariable String name) {
        return ResponseEntity.ok(promotionService.getPromotionsByEmployee(name));
    }

    // ── Get by status ─────────────────────────────────────────────────────────

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Promotion>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(promotionService.getPromotionsByStatus(status));
    }

    // ── Create ────────────────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<?> createPromotion(@Valid @RequestBody Promotion promotion) {
        try {
            Promotion created = promotionService.createPromotion(promotion);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse(e.getMessage()));
        }
    }

    // ── Update status ─────────────────────────────────────────────────────────

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        try {
            String newStatus = body.get("status");
            if (newStatus == null || newStatus.isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse("Status field is required in request body."));
            }
            Promotion updated = promotionService.updateStatus(id, newStatus);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse(e.getMessage()));
        }
    }

    // ── Helper ─────────────────────────────────────────────────────────────────

    private Map<String, String> errorResponse(String message) {
        Map<String, String> err = new HashMap<>();
        err.put("error", message);
        return err;
    }
}

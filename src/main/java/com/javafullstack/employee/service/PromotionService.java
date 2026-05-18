package com.javafullstack.employee.service;

import com.javafullstack.employee.model.Promotion;
import com.javafullstack.employee.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PROMOTION SERVICE — Business Logic
 *
 * Rules enforced:
 *  1. The "projects" field must not be blank — it documents why the
 *     employee deserves the promotion.
 *  2. Status defaults to "Pending" when a new record is created.
 */
@Service
public class PromotionService {

    private final PromotionRepository promotionRepository;

    @Autowired
    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    // ── Get all ───────────────────────────────────────────────────────────────

    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    // ── Get by ID ─────────────────────────────────────────────────────────────

    public Promotion getPromotionById(Long id) {
        return promotionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Promotion not found with id: " + id));
    }

    // ── Get by employee name ──────────────────────────────────────────────────

    public List<Promotion> getPromotionsByEmployee(String name) {
        return promotionRepository.findByNameIgnoreCase(name);
    }

    // ── Get by status ─────────────────────────────────────────────────────────

    public List<Promotion> getPromotionsByStatus(String status) {
        return promotionRepository.findByStatus(status);
    }

    // ── Create ────────────────────────────────────────────────────────────────

    /**
     * Creates a new promotion record.
     *
     * Business rules:
     *  - The "projects" field is REQUIRED.
     *  - Status is set to "Pending" automatically.
     */
    public Promotion createPromotion(Promotion promotion) {
        // Enforce projects field
        if (promotion.getProjects() == null || promotion.getProjects().isBlank()) {
            throw new RuntimeException(
                "Projects field is required. Please describe the work that earned this promotion."
            );
        }

        // Default status to Pending
        if (promotion.getStatus() == null || promotion.getStatus().isBlank()) {
            promotion.setStatus("Pending");
        }

        return promotionRepository.save(promotion);
    }

    // ── Approve / update status ───────────────────────────────────────────────

    public Promotion updateStatus(Long id, String newStatus) {
        Promotion p = getPromotionById(id);
        p.setStatus(newStatus);
        return promotionRepository.save(p);
    }
}

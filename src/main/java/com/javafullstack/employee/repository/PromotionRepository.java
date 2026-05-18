package com.javafullstack.employee.repository;

import com.javafullstack.employee.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PROMOTION REPOSITORY
 *
 * Spring Data JPA auto-generates SQL from these method names.
 */
@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    /** All promotions for a specific employee by name */
    List<Promotion> findByNameIgnoreCase(String name);

    /** All promotions for a department */
    List<Promotion> findByDepartment(String department);

    /** All promotions with a given status ("Pending" / "Approved") */
    List<Promotion> findByStatus(String status);
}

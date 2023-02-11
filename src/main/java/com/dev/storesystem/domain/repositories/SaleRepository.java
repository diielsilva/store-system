package com.dev.storesystem.domain.repositories;

import com.dev.storesystem.domain.entities.SaleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;

public interface SaleRepository extends JpaRepository<SaleEntity, Long> {
    Page<SaleEntity> findByDeletedAtIsNull(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM sale_entity WHERE DATE(created_at) = DATE(?)")
    List<SaleEntity> findTodaySales(OffsetDateTime today);
}

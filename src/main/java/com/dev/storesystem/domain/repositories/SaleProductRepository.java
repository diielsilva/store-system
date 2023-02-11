package com.dev.storesystem.domain.repositories;

import com.dev.storesystem.domain.entities.SaleProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleProductRepository extends JpaRepository<SaleProductEntity, Long> {
    List<SaleProductEntity> findBySaleId(Long id);
}

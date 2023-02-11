package com.dev.storesystem.domain.repositories;

import com.dev.storesystem.domain.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByName(String name);

    Page<ProductEntity> findByNameContainingAndDeletedAtIsNull(String name, Pageable pageable);

    Page<ProductEntity> findByDeletedAtIsNull(Pageable pageable);

    Page<ProductEntity> findByDeletedAtIsNotNull(Pageable pageable);

    Optional<ProductEntity> findByIdAndDeletedAtIsNull(Long id);

    Optional<ProductEntity> findByIdAndDeletedAtIsNotNull(Long id);
}

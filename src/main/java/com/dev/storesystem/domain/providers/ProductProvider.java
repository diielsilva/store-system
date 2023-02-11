package com.dev.storesystem.domain.providers;

import com.dev.storesystem.domain.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductProvider extends AbstractProvider<ProductEntity> {
    Optional<ProductEntity> findByName(String name);

    Page<ProductEntity> findActivesByName(String name, Pageable pageable);

    ProductEntity findById(Long id);
}

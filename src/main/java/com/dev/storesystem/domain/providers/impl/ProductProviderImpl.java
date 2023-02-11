package com.dev.storesystem.domain.providers.impl;

import com.dev.storesystem.domain.entities.ProductEntity;
import com.dev.storesystem.domain.exceptions.EntityNotFound;
import com.dev.storesystem.domain.providers.ProductProvider;
import com.dev.storesystem.domain.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductProviderImpl implements ProductProvider {
    private final ProductRepository repository;

    public ProductProviderImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(ProductEntity entity) {
        repository.save(entity);
    }

    @Override
    public Page<ProductEntity> findAllActive(Pageable pageable) {
        return repository.findByDeletedAtIsNull(pageable);
    }

    @Override
    public Page<ProductEntity> findAllInactive(Pageable pageable) {
        return repository.findByDeletedAtIsNotNull(pageable);
    }

    @Override
    public ProductEntity findActiveById(Long id) throws EntityNotFound {
        return repository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new EntityNotFound("O produto com ID: " + id + " não foi encontrado!"));
    }

    @Override
    public ProductEntity findInactiveById(Long id) throws EntityNotFound {
        return repository.findByIdAndDeletedAtIsNotNull(id)
                .orElseThrow(() -> new EntityNotFound("O produto com ID: " + id + " não foi encontrado!"));
    }

    @Override
    public Optional<ProductEntity> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Page<ProductEntity> findActivesByName(String name, Pageable pageable) {
        return repository.findByNameContainingAndDeletedAtIsNull(name, pageable);
    }

    @Override
    public ProductEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFound("O produto com ID: " + id + " não foi encontrado!"));
    }
}

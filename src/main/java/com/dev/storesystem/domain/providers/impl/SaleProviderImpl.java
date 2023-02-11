package com.dev.storesystem.domain.providers.impl;

import com.dev.storesystem.domain.entities.SaleEntity;
import com.dev.storesystem.domain.exceptions.EntityNotFound;
import com.dev.storesystem.domain.providers.SaleProvider;
import com.dev.storesystem.domain.repositories.SaleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class SaleProviderImpl implements SaleProvider {
    private final SaleRepository repository;

    public SaleProviderImpl(SaleRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(SaleEntity entity) {
        repository.save(entity);
    }

    @Override
    public Page<SaleEntity> findAllActive(Pageable pageable) {
        return repository.findByDeletedAtIsNull(pageable);
    }

    @Override
    public Page<SaleEntity> findAllInactive(Pageable pageable) {
        return null;
    }

    @Override
    public SaleEntity findActiveById(Long id) throws EntityNotFound {
        return null;
    }

    @Override
    public SaleEntity findInactiveById(Long id) throws EntityNotFound {
        return null;
    }

    @Override
    public List<SaleEntity> findTodaySales(OffsetDateTime today) {
        return repository.findTodaySales(today);
    }
}

package com.dev.storesystem.domain.providers.impl;

import com.dev.storesystem.domain.entities.SaleProductEntity;
import com.dev.storesystem.domain.exceptions.EntityNotFound;
import com.dev.storesystem.domain.providers.SaleProductProvider;
import com.dev.storesystem.domain.repositories.SaleProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaleProductProviderImpl implements SaleProductProvider {
    private final SaleProductRepository repository;

    public SaleProductProviderImpl(SaleProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(SaleProductEntity entity) {
        repository.save(entity);
    }

    @Override
    public Page<SaleProductEntity> findAllActive(Pageable pageable) {
        return null;
    }

    @Override
    public Page<SaleProductEntity> findAllInactive(Pageable pageable) {
        return null;
    }

    @Override
    public SaleProductEntity findActiveById(Long id) throws EntityNotFound {
        return null;
    }

    @Override
    public SaleProductEntity findInactiveById(Long id) throws EntityNotFound {
        return null;
    }

    @Override
    public List<SaleProductEntity> findBySaleId(Long id) {
        return repository.findBySaleId(id);
    }
}

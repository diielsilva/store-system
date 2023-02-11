package com.dev.storesystem.domain.providers;

import com.dev.storesystem.domain.entities.SaleProductEntity;

import java.util.List;

public interface SaleProductProvider extends AbstractProvider<SaleProductEntity> {
    List<SaleProductEntity> findBySaleId(Long id);
}

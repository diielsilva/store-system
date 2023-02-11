package com.dev.storesystem.domain.providers;

import com.dev.storesystem.domain.entities.SaleEntity;

import java.time.OffsetDateTime;
import java.util.List;

public interface SaleProvider extends AbstractProvider<SaleEntity> {
    List<SaleEntity> findTodaySales(OffsetDateTime today);
}

package com.dev.storesystem.domain.services;

import com.dev.storesystem.common.dtos.product.SaveProductDto;
import com.dev.storesystem.common.dtos.product.ShowProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ShowProductDto save(SaveProductDto saveProductDto);

    Page<ShowProductDto> findAllActive(Pageable pageable);

    Page<ShowProductDto> findActivesByName(String name, Pageable pageable);

    Page<ShowProductDto> findAllInactive(Pageable pageable);

    ShowProductDto findActiveById(Long id);

    ShowProductDto findInactiveById(Long id);

    ShowProductDto update(Long id, SaveProductDto saveProductDto);

    void restore(Long id);

    void delete(Long id);
}

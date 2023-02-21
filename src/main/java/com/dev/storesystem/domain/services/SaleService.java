package com.dev.storesystem.domain.services;

import com.dev.storesystem.common.dtos.sale.SaveSaleDto;
import com.dev.storesystem.common.dtos.sale.SaveSaleProductDto;
import com.dev.storesystem.common.dtos.sale.ShowSaleDto;
import com.dev.storesystem.common.dtos.sale.ShowSaleProductDto;
import com.dev.storesystem.domain.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SaleService {
    ShowSaleDto save(String username, SaveSaleDto saveSaleDto);

    Page<ShowSaleDto> findAllActive(Pageable pageable);

    List<ShowSaleProductDto> details(Long saleId);

    List<ShowSaleDto> findTodaySales();

    List<ProductEntity> getCartProducts(List<SaveSaleProductDto> saleProducts);
}

package com.dev.storesystem.domain.services.impl;

import com.dev.storesystem.common.dtos.sale.SaveSaleDto;
import com.dev.storesystem.common.dtos.sale.SaveSaleProductDto;
import com.dev.storesystem.common.dtos.sale.ShowSaleDto;
import com.dev.storesystem.common.dtos.sale.ShowSaleProductDto;
import com.dev.storesystem.common.mappers.EntityMapper;
import com.dev.storesystem.domain.entities.SaleProductEntity;
import com.dev.storesystem.domain.exceptions.InsufficientAmount;
import com.dev.storesystem.domain.exceptions.RepeatedProductsReceived;
import com.dev.storesystem.domain.helpers.DateHelper;
import com.dev.storesystem.domain.providers.ProductProvider;
import com.dev.storesystem.domain.providers.SaleProductProvider;
import com.dev.storesystem.domain.providers.SaleProvider;
import com.dev.storesystem.domain.providers.UserProvider;
import com.dev.storesystem.domain.services.SaleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleProvider saleProvider;
    private final SaleProductProvider saleProductProvider;
    private final ProductProvider productProvider;
    private final UserProvider userProvider;
    private final EntityMapper entityMapper;
    private final DateHelper dateHelper;

    public SaleServiceImpl(SaleProvider saleProvider, SaleProductProvider saleProductProvider,
                           ProductProvider productProvider, UserProvider userProvider, EntityMapper entityMapper,
                           DateHelper dateHelper) {
        this.saleProvider = saleProvider;
        this.saleProductProvider = saleProductProvider;
        this.productProvider = productProvider;
        this.userProvider = userProvider;
        this.entityMapper = entityMapper;
        this.dateHelper = dateHelper;
    }

    @Transactional
    @Override
    public ShowSaleDto save(String username, SaveSaleDto saveSaleDto) {
        var userEntity = userProvider.findActiveByUsername(username);
        var saleEntity = entityMapper.mapFromSaveSaleDtoToSaleEntity(saveSaleDto);
        saleEntity.setUser(userEntity);
        if (haveRepeatedProducts(saveSaleDto.getProducts())) {
            throw new RepeatedProductsReceived("O carrinho possui produtos repetidos!");
        }
        saleEntity.setTotal(calculateTotalSale(saveSaleDto.getProducts()));
        dateHelper.setSaveTime(saleEntity);
        saleProvider.save(saleEntity);
        for (SaveSaleProductDto productDto : saveSaleDto.getProducts()) {
            var productEntity = productProvider.findActiveById(productDto.getProductId());
            var saleProductEntity = new SaleProductEntity();
            if (productDto.getAmount() > productEntity.getAmount()) {
                throw new InsufficientAmount("A quantidade do produto: " + productEntity.getName() + " é insuficiente!");
            }
            saleProductEntity.setSale(saleEntity);
            saleProductEntity.setProduct(productEntity);
            saleProductEntity.setAmount(productDto.getAmount());
            saleProductEntity.setPriceAtSale(productEntity.getPrice());
            dateHelper.setSaveTime(saleProductEntity);
            saleProductProvider.save(saleProductEntity);
            productEntity.setAmount(productEntity.getAmount() - productDto.getAmount());
            dateHelper.setUpdateTime(productEntity);
            productProvider.save(productEntity);
        }
        return entityMapper.mapFromSaleEntityToShowSaleDto(saleEntity);
    }

    @Override
    public Page<ShowSaleDto> findAllActive(Pageable pageable) {
        var activeSales = saleProvider.findAllActive(pageable);
        return activeSales.map(entityMapper::mapFromSaleEntityToShowSaleDto);
    }

    @Override
    public List<ShowSaleProductDto> details(Long saleId) {
        var saleProducts = saleProductProvider.findBySaleId(saleId);
        return saleProducts.stream().map(entityMapper::mapFromSaleProductEntityToShowSaleProductDto).toList();
    }

    @Override
    public List<ShowSaleDto> findTodaySales() {
        var today = OffsetDateTime.now();
        var sales = saleProvider.findTodaySales(today);
        return sales.stream().map(entityMapper::mapFromSaleEntityToShowSaleDto).toList();
    }

    private BigDecimal calculateTotalSale(List<SaveSaleProductDto> products) {
        var total = new BigDecimal(0);
        for (SaveSaleProductDto productDto : products) {
            var productEntity = productProvider.findActiveById(productDto.getProductId());
            total = total.add(productEntity.getPrice().multiply(new BigDecimal(productDto.getAmount())));
        }
        return total;
    }

    private boolean haveRepeatedProducts(List<SaveSaleProductDto> products) {
        var haveRepeatedProducts = false;
        for (int a = 0; a < products.size(); a++) {
            var productId = products.get(a).getProductId();
            var repeatTimes = 0;
            for (SaveSaleProductDto product : products) {
                if (product.getProductId().equals(productId)) {
                    repeatTimes++;
                }
                if (repeatTimes > 1) {
                    haveRepeatedProducts = true;
                }
            }
        }
        return haveRepeatedProducts;
    }
}

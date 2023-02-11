package com.dev.storesystem.common.dtos.sale;

import com.dev.storesystem.common.dtos.ShowEntityDto;
import com.dev.storesystem.common.dtos.product.ShowProductDto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class ShowSaleProductDto extends ShowEntityDto {
    private ShowProductDto product;
    private BigDecimal priceAtSale;
    private Long amount;

    public ShowSaleProductDto(Long id, OffsetDateTime createdAt, OffsetDateTime updatedAt, OffsetDateTime deletedAt) {
        super(id, createdAt, updatedAt, deletedAt);
    }

    public ShowProductDto getProduct() {
        return product;
    }

    public void setProduct(ShowProductDto product) {
        this.product = product;
    }

    public BigDecimal getPriceAtSale() {
        return priceAtSale;
    }

    public void setPriceAtSale(BigDecimal priceAtSale) {
        this.priceAtSale = priceAtSale;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}

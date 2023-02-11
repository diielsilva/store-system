package com.dev.storesystem.common.dtos.product;

import com.dev.storesystem.common.dtos.ShowEntityDto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class ShowProductDto extends ShowEntityDto {
    private String name;
    private BigDecimal price;
    private Long amount;

    public ShowProductDto(Long id, OffsetDateTime createdAt, OffsetDateTime updatedAt, OffsetDateTime deletedAt) {
        super(id, createdAt, updatedAt, deletedAt);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}

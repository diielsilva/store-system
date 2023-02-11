package com.dev.storesystem.common.dtos.sale;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class SaveSaleProductDto {
    @NotNull(message = "A identificação do produto não pode ser nula!")
    @Min(value = 1L, message = "O ID do produto não pode ser menor que um!")
    private Long productId;
    @NotNull(message = "A quantidade do produto não pode ser nula!")
    @Min(value = 1L, message = "A quantidade do produto não pode ser menor que um!")
    private Long amount;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}

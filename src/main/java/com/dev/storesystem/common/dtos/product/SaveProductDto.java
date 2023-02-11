package com.dev.storesystem.common.dtos.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class SaveProductDto {
    @NotBlank(message = "O nome do produto não pode ser nulo!")
    private String name;
    @NotNull(message = "O preço do produto não pode ser nulo!")
    @DecimalMin(value = "0.0", message = "O preço do produto não pode ser menor que zero!")
    private BigDecimal price;
    @NotNull(message = "A quantidade do produto não pode ser nula!")
    @Min(value = 1L, message = "A quantidade do produto não pode ser menor que zero!")
    private Long amount;

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

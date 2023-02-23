package com.dev.storesystem.common.dtos.sale;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ShowPdfSaleDto {
    private Double percentDiscount;
    @NotNull(message = "Os produtos da venda não podem ser nulos!")
    @NotEmpty(message = "A quantidade de produtos não pode ser menor que um!")
    @Valid
    private List<SaveSaleProductDto> products;

    public Double getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(Double percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    public List<SaveSaleProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<SaveSaleProductDto> products) {
        this.products = products;
    }
}

package com.dev.storesystem.common.dtos.sale;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class SaveSaleDto {
    @NotBlank(message = "O método de pagamento não pode ser nulo!")
    private String paymentType;
    @NotNull(message = "Os produtos da venda não podem ser nulos!")
    @NotEmpty(message = "A quantidade de produtos não pode ser menor que um!")
    @Valid
    private List<SaveSaleProductDto> products;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public List<SaveSaleProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<SaveSaleProductDto> products) {
        this.products = products;
    }
}

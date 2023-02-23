package com.dev.storesystem.common.dtos.sale;

import com.dev.storesystem.common.dtos.ShowEntityDto;
import com.dev.storesystem.common.dtos.user.ShowUserDto;
import com.dev.storesystem.domain.enums.PaymentType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class ShowSaleDto extends ShowEntityDto {
    private ShowUserDto user;
    private BigDecimal total;
    private PaymentType paymentType;
    private BigDecimal discount;
    private Double percentDiscount;

    public ShowSaleDto(Long id, OffsetDateTime createdAt, OffsetDateTime updatedAt, OffsetDateTime deletedAt) {
        super(id, createdAt, updatedAt, deletedAt);
    }

    public ShowUserDto getUser() {
        return user;
    }

    public void setUser(ShowUserDto user) {
        this.user = user;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Double getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(Double percentDiscount) {
        this.percentDiscount = percentDiscount;
    }
}

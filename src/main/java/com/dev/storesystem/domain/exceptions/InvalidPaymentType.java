package com.dev.storesystem.domain.exceptions;

public class InvalidPaymentType extends BusinessException {
    public InvalidPaymentType(String message) {
        super(message);
    }
}

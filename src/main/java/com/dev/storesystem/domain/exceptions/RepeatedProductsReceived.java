package com.dev.storesystem.domain.exceptions;

public class RepeatedProductsReceived extends BusinessException{
    public RepeatedProductsReceived(String message) {
        super(message);
    }
}

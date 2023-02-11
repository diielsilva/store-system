package com.dev.storesystem.domain.exceptions;

public class InsufficientAmount extends BusinessException{
    public InsufficientAmount(String message) {
        super(message);
    }
}

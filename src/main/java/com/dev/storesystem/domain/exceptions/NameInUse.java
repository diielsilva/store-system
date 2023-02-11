package com.dev.storesystem.domain.exceptions;

public class NameInUse extends BusinessException {
    public NameInUse(String message) {
        super(message);
    }
}

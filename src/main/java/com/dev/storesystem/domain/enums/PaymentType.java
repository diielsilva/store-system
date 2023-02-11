package com.dev.storesystem.domain.enums;

import com.dev.storesystem.domain.exceptions.InvalidPaymentType;

public enum PaymentType {
    CARD,
    CASH,
    PIX;

    public static PaymentType toEnum(String content) {
        return switch (content) {
            case "CARD" -> CARD;
            case "CASH" -> CASH;
            case "PIX" -> PIX;
            default -> throw new InvalidPaymentType("O método de pagamento: " + content + " é inválido!");
        };
    }
}

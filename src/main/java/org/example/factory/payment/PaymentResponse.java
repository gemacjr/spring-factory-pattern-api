package org.example.factory.payment;

import java.math.BigDecimal;

public record PaymentResponse(
        String transactionId,
        PaymentMethod method,
        BigDecimal amount,
        BigDecimal fee,
        BigDecimal total,
        String currency,
        String status) {
}

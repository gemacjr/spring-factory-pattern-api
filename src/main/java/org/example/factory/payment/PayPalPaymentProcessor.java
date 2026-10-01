package org.example.factory.payment;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
class PayPalPaymentProcessor implements PaymentProcessor {

    private static final BigDecimal RATE = new BigDecimal("0.0349");
    private static final BigDecimal FIXED_FEE = new BigDecimal("0.49");

    @Override
    public PaymentMethod method() {
        return PaymentMethod.PAYPAL;
    }

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return amount.multiply(RATE).add(FIXED_FEE);
    }

    @Override
    public String transactionPrefix() {
        return "PP";
    }
}

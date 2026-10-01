package org.example.factory.payment;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
class UpiPaymentProcessor implements PaymentProcessor {

    @Override
    public PaymentMethod method() {
        return PaymentMethod.UPI;
    }

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        // UPI person-to-merchant transfers carry no customer fee.
        return BigDecimal.ZERO;
    }
}

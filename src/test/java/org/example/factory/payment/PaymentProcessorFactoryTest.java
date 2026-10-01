package org.example.factory.payment;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

class PaymentProcessorFactoryTest {

    private final PaymentProcessorFactory factory = new PaymentProcessorFactory(List.of(
            new CreditCardPaymentProcessor(), new PayPalPaymentProcessor(), new UpiPaymentProcessor()));

    @Test
    void eachMethodAppliesItsOwnFeeRules() {
        assertThat(pay(PaymentMethod.CREDIT_CARD).fee()).isEqualByComparingTo("3.20");
        assertThat(pay(PaymentMethod.PAYPAL).fee()).isEqualByComparingTo("3.98");
        assertThat(pay(PaymentMethod.UPI).fee()).isEqualByComparingTo("0.00");
    }

    @Test
    void totalIsAmountPlusFee() {
        PaymentResponse response = pay(PaymentMethod.CREDIT_CARD);

        assertThat(response.total()).isEqualByComparingTo("103.20");
        assertThat(response.transactionId()).startsWith("CC-");
        assertThat(response.currency()).isEqualTo("USD");
    }

    private PaymentResponse pay(PaymentMethod method) {
        return factory.getProcessor(method).process(new PaymentRequest(method, new BigDecimal("100"), null));
    }
}

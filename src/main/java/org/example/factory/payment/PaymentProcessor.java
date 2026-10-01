package org.example.factory.payment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

/**
 * Product interface for payment processing. Each payment method supplies its own
 * fee rules and transaction ID format; the charging flow itself is shared.
 */
public interface PaymentProcessor {

    PaymentMethod method();

    BigDecimal calculateFee(BigDecimal amount);

    default String transactionPrefix() {
        return method().name();
    }

    default PaymentResponse process(PaymentRequest request) {
        BigDecimal amount = request.amount().setScale(2, RoundingMode.HALF_UP);
        BigDecimal fee = calculateFee(amount).setScale(2, RoundingMode.HALF_UP);
        String transactionId = transactionPrefix() + "-" + UUID.randomUUID();
        return new PaymentResponse(transactionId, method(), amount, fee, amount.add(fee),
                request.currencyOrDefault(), "APPROVED");
    }
}

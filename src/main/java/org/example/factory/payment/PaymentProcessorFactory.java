package org.example.factory.payment;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.example.factory.common.UnsupportedTypeException;
import org.springframework.stereotype.Component;

/**
 * Factory: returns the {@link PaymentProcessor} for a given {@link PaymentMethod}.
 */
@Component
public class PaymentProcessorFactory {

    private final Map<PaymentMethod, PaymentProcessor> processors = new EnumMap<>(PaymentMethod.class);

    public PaymentProcessorFactory(List<PaymentProcessor> processorBeans) {
        for (PaymentProcessor processor : processorBeans) {
            PaymentProcessor previous = processors.put(processor.method(), processor);
            if (previous != null) {
                throw new IllegalStateException("Duplicate PaymentProcessor for method " + processor.method()
                        + ": " + previous.getClass().getSimpleName() + " and " + processor.getClass().getSimpleName());
            }
        }
    }

    public PaymentProcessor getProcessor(PaymentMethod method) {
        PaymentProcessor processor = processors.get(method);
        if (processor == null) {
            throw new UnsupportedTypeException("No payment processor registered for method " + method);
        }
        return processor;
    }

    public Set<PaymentMethod> supportedMethods() {
        return processors.keySet();
    }
}

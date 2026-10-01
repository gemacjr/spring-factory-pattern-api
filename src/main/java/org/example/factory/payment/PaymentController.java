package org.example.factory.payment;

import java.util.Set;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentProcessorFactory factory;

    public PaymentController(PaymentProcessorFactory factory) {
        this.factory = factory;
    }

    @PostMapping
    public PaymentResponse pay(@Valid @RequestBody PaymentRequest request) {
        return factory.getProcessor(request.method()).process(request);
    }

    @GetMapping("/methods")
    public Set<PaymentMethod> methods() {
        return factory.supportedMethods();
    }
}

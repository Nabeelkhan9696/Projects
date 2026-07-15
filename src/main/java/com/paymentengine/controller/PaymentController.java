package com.paymentengine.controller;

import com.paymentengine.domain.Payment;
import com.paymentengine.dto.PaymentRequest;
import com.paymentengine.dto.PaymentResponse;
import com.paymentengine.service.IdempotencyService;
import com.paymentengine.service.PaymentOrchestrator;
import com.paymentengine.service.PaymentService;
import com.paymentengine.util.PaymentMapper;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private static final String IDEMPOTENCY_HEADER = "Idempotency-Key";

    private final PaymentOrchestrator paymentOrchestrator;
    private final PaymentService paymentService;
    private final IdempotencyService idempotencyService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @RequestHeader(name = IDEMPOTENCY_HEADER) String idempotencyKey,
            @Valid @RequestBody PaymentRequest request) {

        String requestHash = idempotencyService.hashRequest(request);
        return idempotencyService.findStoredResponse(idempotencyKey, requestHash)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    Payment payment = paymentOrchestrator.orchestrateCreation(request);
                    PaymentResponse response = PaymentMapper.toPaymentResponse(payment);
                    idempotencyService.storeResponse(idempotencyKey, requestHash, response);
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                });
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable UUID id) {
        Payment payment = paymentService.getById(id);
        return ResponseEntity.ok(PaymentMapper.toPaymentResponse(payment));
    }
}

package com.paymentengine.service;

import com.paymentengine.domain.Payment;
import com.paymentengine.domain.enums.PaymentStatus;
import com.paymentengine.dto.PaymentRequest;
import com.paymentengine.repository.PaymentRepository;
import com.paymentengine.util.PaymentMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public Payment createPayment(PaymentRequest request) {
        paymentRepository.findByExternalId(request.getExternalId()).ifPresent(existing -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Payment with externalId already exists");
        });
        Payment payment = PaymentMapper.toPaymentEntity(request);
        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment updateStatus(Payment payment, PaymentStatus status, String failureReason) {
        payment.setStatus(status);
        payment.setFailureReason(failureReason);
        return paymentRepository.save(payment);
    }

    @Transactional(readOnly = true)
    public Payment getById(UUID id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));
    }
}

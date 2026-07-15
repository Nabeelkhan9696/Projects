package com.paymentengine.service;

import com.paymentengine.domain.Payment;
import com.paymentengine.domain.PaymentMethod;
import com.paymentengine.domain.enums.PaymentStatus;
import com.paymentengine.dto.PaymentRequest;
import com.paymentengine.repository.PaymentMethodRepository;
import com.paymentengine.util.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentOrchestrator {

    private final PaymentService paymentService;
    private final PaymentMethodRepository paymentMethodRepository;

    @Transactional
    public Payment orchestrateCreation(PaymentRequest request) {
        Payment payment = paymentService.createPayment(request);

        PaymentMethod method = PaymentMapper.toPaymentMethod(request.getPaymentMethod());
        method.setPayment(payment);
        paymentMethodRepository.save(method);

        paymentService.updateStatus(payment, PaymentStatus.AUTHORIZED, null);
        return paymentService.updateStatus(payment, PaymentStatus.CAPTURED, null);
    }
}

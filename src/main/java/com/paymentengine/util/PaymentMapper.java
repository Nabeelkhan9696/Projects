package com.paymentengine.util;

import com.paymentengine.domain.Payment;
import com.paymentengine.domain.PaymentMethod;
import com.paymentengine.domain.enums.PaymentStatus;
import com.paymentengine.dto.PaymentMethodRequest;
import com.paymentengine.dto.PaymentRequest;
import com.paymentengine.dto.PaymentResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;

public final class PaymentMapper {

    private PaymentMapper() {
    }

    public static Payment toPaymentEntity(PaymentRequest request) {
        return Payment.builder()
                .externalId(request.getExternalId())
                .amount(request.getAmount().setScale(2, RoundingMode.HALF_UP))
                .currency(request.getCurrency())
                .status(PaymentStatus.INITIATED)
                .methodType(request.getPaymentMethod().getType())
                .customerId(request.getCustomerId())
                .build();
    }

    public static PaymentMethod toPaymentMethod(PaymentMethodRequest request) {
        return PaymentMethod.builder()
                .type(request.getType())
                .maskedCardNumber(maskCard(request.getNumber()))
                .expiryMonth(request.getExpiryMonth())
                .expiryYear(request.getExpiryYear())
                .holderName(request.getHolderName())
                .bankAccountNumber(request.getBankAccountNumber())
                .routingNumber(request.getRoutingNumber())
                .build();
    }

    public static PaymentResponse toPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .externalId(payment.getExternalId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .status(payment.getStatus())
                .methodType(payment.getMethodType())
                .customerId(payment.getCustomerId())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .failureReason(payment.getFailureReason())
                .build();
    }

    private static String maskCard(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return null;
        }
        String suffix = cardNumber.substring(cardNumber.length() - 4);
        return "**** **** **** " + suffix;
    }
}

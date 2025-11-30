package com.paymentengine.dto;

import com.paymentengine.domain.enums.PaymentMethodType;
import com.paymentengine.domain.enums.PaymentStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PaymentResponse {
    UUID id;
    String externalId;
    BigDecimal amount;
    String currency;
    PaymentStatus status;
    PaymentMethodType methodType;
    String customerId;
    Instant createdAt;
    Instant updatedAt;
    String failureReason;
}

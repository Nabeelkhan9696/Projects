package com.paymentengine.dto;

import com.paymentengine.domain.enums.PaymentMethodType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PaymentMethodRequest {

    @NotNull
    private PaymentMethodType type;

    @Size(min = 12, max = 19)
    private String number;

    @Min(1)
    @Max(12)
    private Integer expiryMonth;

    @Min(2024)
    @Max(2100)
    private Integer expiryYear;

    @NotBlank
    private String holderName;

    private String bankAccountNumber;

    private String routingNumber;
}

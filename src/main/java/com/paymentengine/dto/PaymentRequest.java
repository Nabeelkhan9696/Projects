package com.paymentengine.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentRequest {

    @NotBlank
    private String externalId;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be ISO 4217 uppercase code")
    private String currency;

    @NotBlank
    private String customerId;

    @Valid
    @NotNull
    private PaymentMethodRequest paymentMethod;
}

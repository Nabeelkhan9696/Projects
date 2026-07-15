package com.paymentengine.repository;

import com.paymentengine.domain.PaymentMethod;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID> {
}

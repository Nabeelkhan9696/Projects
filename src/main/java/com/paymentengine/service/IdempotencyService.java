package com.paymentengine.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentengine.domain.IdempotencyKey;
import com.paymentengine.dto.PaymentRequest;
import com.paymentengine.dto.PaymentResponse;
import com.paymentengine.repository.IdempotencyKeyRepository;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IdempotencyService {

    private final IdempotencyKeyRepository idempotencyKeyRepository;
    private final ObjectMapper objectMapper;

    @Value("${idempotency.ttl-hours:24}")
    private long idempotencyTtlHours;

    @Transactional(readOnly = true)
    public Optional<PaymentResponse> findStoredResponse(String key, String requestHash) {
        return idempotencyKeyRepository.findById(key)
                .filter(entry -> entry.getExpiresAt().isAfter(Instant.now()))
                .filter(entry -> entry.getRequestHash().equals(requestHash))
                .flatMap(this::deserializeResponse);
    }

    @Transactional
    public void storeResponse(String key, String requestHash, PaymentResponse response) {
        IdempotencyKey entry = IdempotencyKey.builder()
                .key(key)
                .requestHash(requestHash)
                .responseBody(writeAsString(response))
                .status(response.getStatus().name())
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plus(idempotencyTtlHours, ChronoUnit.HOURS))
                .build();
        idempotencyKeyRepository.save(entry);
    }

    public String hashRequest(PaymentRequest request) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(objectMapper.writeValueAsBytes(request));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException | JsonProcessingException e) {
            throw new IllegalStateException("Unable to hash request", e);
        }
    }

    private Optional<PaymentResponse> deserializeResponse(IdempotencyKey entry) {
        try {
            return Optional.of(objectMapper.readValue(entry.getResponseBody(), PaymentResponse.class));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    private String writeAsString(PaymentResponse response) {
        try {
            return objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Unable to serialize response", e);
        }
    }
}

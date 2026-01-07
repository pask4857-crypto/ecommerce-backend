package com.example.backend.payment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.payment.entity.Payment;
import com.example.backend.payment.entity.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByOrderId(Long orderId);

    Optional<Payment> findByMerchantTradeNo(String merchantTradeNo);

    boolean existsByOrderIdAndPaymentStatus(Long orderId, PaymentStatus paymentStatus);

}

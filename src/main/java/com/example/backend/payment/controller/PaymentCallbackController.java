package com.example.backend.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import com.example.backend.payment.service.PaymentService;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentCallbackController {

    private final PaymentService paymentService;

    @PostMapping("/ecpay/callback")
    public ResponseEntity<String> handleEcpayCallback(@RequestParam Map<String, String> params) {

        // 綠界會送一堆欄位
        paymentService.processEcpayCallback(params);

        // 印出回傳參數
        System.out.println("⚡ ECPay callback:" + params);

        // 回傳 SUCCESS 才算處理成功
        return ResponseEntity.ok("1|OK");
    }
}

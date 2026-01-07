package com.example.backend.payment.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class EcpayHashUtil {
    public static String generateCheckMacValue(Map<String, String> params, String hashKey, String hashIV) {
        SortedMap<String, String> sorted = new TreeMap<>(params);

        StringBuilder sb = new StringBuilder("HashKey=").append(hashKey);
        sorted.forEach((k, v) -> sb.append("&").append(k).append("=").append(v));
        sb.append("&HashIV=").append(hashIV);

        try {
            String encoded = URLEncoder.encode(sb.toString(), StandardCharsets.UTF_8.toString())
                    .toLowerCase();

            encoded = encoded
                    .replace("%21", "!")
                    .replace("%28", "(")
                    .replace("%29", ")")
                    .replace("%2a", "*")
                    .replace("%20", "+");

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(encoded.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();

            StringBuilder hex = new StringBuilder();
            for (byte b : digest) {
                hex.append(String.format("%02X", b));
            }

            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException("CheckMacValue 計算失敗", e);
        }
    }
}

package com.exam.productservice.messaging;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class PharmacyAlertPublisher {
    public static final String CHANNEL = "pharmacy-alerts";

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public PharmacyAlertPublisher(
            StringRedisTemplate redisTemplate,
            ObjectMapper objectMapper
    ) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishImportAlert(String medicineName, int quantity) {
        PharmacyAlert alert = new PharmacyAlert(
                "IMPORT",
                "Đã nhập " + quantity + " hộp " + medicineName
        );

        try {
            String json = objectMapper.writeValueAsString(alert);

            redisTemplate.convertAndSend(CHANNEL, json);

            System.out.println("[PUBLISHER] Đã gửi: " + json);
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Không thể tạo JSON thông báo nhập hàng", e
            );
        }
    }
}

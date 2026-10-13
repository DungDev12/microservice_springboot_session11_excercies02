package com.exam.productservice.controller;

import com.exam.productservice.messaging.PharmacyAlertPublisher;
import com.exam.productservice.messaging.PharmacyAlertResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
public class PharmacyAlertController {

    private final PharmacyAlertPublisher publisher;


    @PostMapping("/import")
    public PharmacyAlertResponse importMedicine(
            @RequestParam(defaultValue = "Panadol") String name,
            @RequestParam(defaultValue = "100") int quantity
    ) {
        if (name.isBlank()) {
            throw new IllegalArgumentException(
                    "Tên thuốc không được để trống"
            );
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Số lượng nhập phải lớn hơn 0"
            );
        }

        publisher.publishImportAlert(name, quantity);

        return new PharmacyAlertResponse(
                "SUCCESS",
                "Đã gửi thông báo nhập hàng"
        );
    }
}

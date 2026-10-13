package com.exam.productservice.messaging;

public record PharmacyAlert(
        String type,
        String message
) {
}

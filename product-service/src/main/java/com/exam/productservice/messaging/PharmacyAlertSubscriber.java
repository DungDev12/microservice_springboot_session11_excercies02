package com.exam.productservice.messaging;

import org.jspecify.annotations.Nullable;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class PharmacyAlertSubscriber implements MessageListener {
    @Override
    public void onMessage(Message message, byte @Nullable [] pattern) {
        String json = new String(
                message.getBody(),
                StandardCharsets.UTF_8
        );

        System.out.println();
        System.out.println("========== PHARMACY ALERT ==========");
        System.out.println("Channel: "
                + new String(
                message.getChannel(),
                StandardCharsets.UTF_8
        ));
        System.out.println("Received JSON: " + json);
        System.out.println("====================================");
        System.out.println();
    }
}

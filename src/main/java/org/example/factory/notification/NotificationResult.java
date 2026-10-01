package org.example.factory.notification;

import java.time.Instant;

public record NotificationResult(
        NotificationChannel channel,
        String recipient,
        String status,
        String detail,
        Instant sentAt) {

    public static NotificationResult sent(NotificationChannel channel, String recipient, String detail) {
        return new NotificationResult(channel, recipient, "SENT", detail, Instant.now());
    }
}

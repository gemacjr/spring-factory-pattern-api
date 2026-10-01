package org.example.factory.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class PushNotificationSender implements NotificationSender {

    private static final Logger log = LoggerFactory.getLogger(PushNotificationSender.class);

    @Override
    public NotificationChannel channel() {
        return NotificationChannel.PUSH;
    }

    @Override
    public NotificationResult send(NotificationRequest request) {
        // A real implementation would call FCM/APNs using the recipient's device token.
        log.info("Sending PUSH to device {}: {}", request.recipient(), request.message());
        return NotificationResult.sent(channel(), request.recipient(), "Push delivered to device");
    }
}

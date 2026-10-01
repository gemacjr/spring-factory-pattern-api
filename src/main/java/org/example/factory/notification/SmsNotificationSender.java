package org.example.factory.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class SmsNotificationSender implements NotificationSender {

    private static final Logger log = LoggerFactory.getLogger(SmsNotificationSender.class);
    private static final int SMS_MAX_LENGTH = 160;

    @Override
    public NotificationChannel channel() {
        return NotificationChannel.SMS;
    }

    @Override
    public NotificationResult send(NotificationRequest request) {
        String body = request.message().length() > SMS_MAX_LENGTH
                ? request.message().substring(0, SMS_MAX_LENGTH)
                : request.message();
        log.info("Sending SMS to {}: {}", request.recipient(), body);
        return NotificationResult.sent(channel(), request.recipient(),
                "SMS dispatched (" + body.length() + " chars)");
    }
}

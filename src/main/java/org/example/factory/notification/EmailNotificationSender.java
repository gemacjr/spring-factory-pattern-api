package org.example.factory.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class EmailNotificationSender implements NotificationSender {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationSender.class);

    @Override
    public NotificationChannel channel() {
        return NotificationChannel.EMAIL;
    }

    @Override
    public NotificationResult send(NotificationRequest request) {
        // A real implementation would call an SMTP server or a provider such as SES/SendGrid.
        log.info("Sending EMAIL to {}: {}", request.recipient(), request.message());
        return NotificationResult.sent(channel(), request.recipient(), "Email queued via SMTP gateway");
    }
}

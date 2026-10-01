package org.example.factory.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class SlackNotificationSender implements NotificationSender {

    private static final Logger log = LoggerFactory.getLogger(SlackNotificationSender.class);

    @Override
    public NotificationChannel channel() {
        return NotificationChannel.SLACK;
    }

    @Override
    public NotificationResult send(NotificationRequest request) {
        // "#name" posts to a channel, "@name" sends a DM; a bare name is treated as a channel.
        String target = request.recipient().startsWith("#") || request.recipient().startsWith("@")
                ? request.recipient()
                : "#" + request.recipient();
        String kind = target.startsWith("@") ? "direct message" : "channel message";
        // A real implementation would call Slack's chat.postMessage API or an incoming webhook.
        log.info("Sending SLACK {} to {}: {}", kind, target, request.message());
        return NotificationResult.sent(channel(), target, "Slack " + kind + " posted");
    }
}

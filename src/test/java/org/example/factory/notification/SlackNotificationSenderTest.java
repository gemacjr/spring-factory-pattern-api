package org.example.factory.notification;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SlackNotificationSenderTest {

    private final SlackNotificationSender sender = new SlackNotificationSender();

    @Test
    void postsToChannel() {
        NotificationResult result = send("#deployments");

        assertThat(result.recipient()).isEqualTo("#deployments");
        assertThat(result.detail()).contains("channel message");
    }

    @Test
    void sendsDirectMessageToUser() {
        NotificationResult result = send("@alex");

        assertThat(result.recipient()).isEqualTo("@alex");
        assertThat(result.detail()).contains("direct message");
    }

    @Test
    void treatsBareNameAsChannel() {
        assertThat(send("general").recipient()).isEqualTo("#general");
    }

    private NotificationResult send(String recipient) {
        return sender.send(new NotificationRequest(NotificationChannel.SLACK, recipient, "Build passed"));
    }
}

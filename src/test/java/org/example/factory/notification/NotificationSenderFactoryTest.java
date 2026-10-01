package org.example.factory.notification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.example.factory.common.UnsupportedTypeException;
import org.junit.jupiter.api.Test;

class NotificationSenderFactoryTest {

    @Test
    void returnsSenderMatchingChannel() {
        var factory = new NotificationSenderFactory(List.of(
                new EmailNotificationSender(), new SmsNotificationSender(), new PushNotificationSender(),
                new SlackNotificationSender()));

        assertThat(factory.getSender(NotificationChannel.EMAIL)).isInstanceOf(EmailNotificationSender.class);
        assertThat(factory.getSender(NotificationChannel.SMS)).isInstanceOf(SmsNotificationSender.class);
        assertThat(factory.getSender(NotificationChannel.PUSH)).isInstanceOf(PushNotificationSender.class);
        assertThat(factory.getSender(NotificationChannel.SLACK)).isInstanceOf(SlackNotificationSender.class);
    }

    @Test
    void throwsForUnregisteredChannel() {
        var factory = new NotificationSenderFactory(List.of(new EmailNotificationSender()));

        assertThatThrownBy(() -> factory.getSender(NotificationChannel.SMS))
                .isInstanceOf(UnsupportedTypeException.class)
                .hasMessageContaining("SMS");
    }

    @Test
    void rejectsDuplicateImplementationsAtStartup() {
        assertThatThrownBy(() -> new NotificationSenderFactory(List.of(
                new EmailNotificationSender(), new EmailNotificationSender())))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Duplicate");
    }
}

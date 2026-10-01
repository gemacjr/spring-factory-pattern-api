package org.example.factory.notification;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.example.factory.common.UnsupportedTypeException;
import org.springframework.stereotype.Component;

/**
 * Factory: returns the right {@link NotificationSender} for a channel.
 *
 * <p>Instead of a switch statement calling {@code new}, Spring injects every
 * {@code NotificationSender} bean and the factory indexes them by channel.
 * Adding a channel means adding one {@code @Component}; this class never changes.
 */
@Component
public class NotificationSenderFactory {

    private final Map<NotificationChannel, NotificationSender> senders = new EnumMap<>(NotificationChannel.class);

    public NotificationSenderFactory(List<NotificationSender> senderBeans) {
        for (NotificationSender sender : senderBeans) {
            NotificationSender previous = senders.put(sender.channel(), sender);
            if (previous != null) {
                throw new IllegalStateException("Duplicate NotificationSender for channel " + sender.channel()
                        + ": " + previous.getClass().getSimpleName() + " and " + sender.getClass().getSimpleName());
            }
        }
    }

    public NotificationSender getSender(NotificationChannel channel) {
        NotificationSender sender = senders.get(channel);
        if (sender == null) {
            throw new UnsupportedTypeException("No notification sender registered for channel " + channel);
        }
        return sender;
    }

    public Set<NotificationChannel> supportedChannels() {
        return senders.keySet();
    }
}

package org.example.factory.notification;

/**
 * Product interface: every notification channel implements this.
 * Callers depend only on this abstraction, never on a concrete sender.
 */
public interface NotificationSender {

    /** The channel this implementation handles; used by the factory as its lookup key. */
    NotificationChannel channel();

    NotificationResult send(NotificationRequest request);
}

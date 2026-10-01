package org.example.factory.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificationRequest(
        @NotNull NotificationChannel channel,
        @NotBlank String recipient,
        @NotBlank String message) {
}

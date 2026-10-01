package org.example.factory.notification;

import java.util.Set;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationSenderFactory factory;

    public NotificationController(NotificationSenderFactory factory) {
        this.factory = factory;
    }

    @PostMapping
    public NotificationResult send(@Valid @RequestBody NotificationRequest request) {
        return factory.getSender(request.channel()).send(request);
    }

    @GetMapping("/channels")
    public Set<NotificationChannel> channels() {
        return factory.supportedChannels();
    }
}

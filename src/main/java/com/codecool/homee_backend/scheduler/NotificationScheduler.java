package com.codecool.homee_backend.scheduler;

import com.codecool.homee_backend.controller.dto.notification.NewNotificationDto;
import com.codecool.homee_backend.entity.Event;
import com.codecool.homee_backend.entity.HomeeUser;
import com.codecool.homee_backend.service.EventService;
import com.codecool.homee_backend.service.NotificationService;
import com.codecool.homee_backend.service.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class NotificationScheduler {

    private final EventService eventService;

    private final EmailService emailService;

    private final NotificationService notificationService;


    public NotificationScheduler(EventService eventService, EmailService emailService, NotificationService notificationService) {
        this.eventService = eventService;
        this.emailService = emailService;
        this.notificationService = notificationService;
    }


    @Async
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void sendNotifications() throws MessagingException {
        log.info("Sending notifications...");
        List<Event> eventsForDate = eventService.getEventEntitiesForDate(LocalDate.now());
        for (Event event : eventsForDate) {
            Set<HomeeUser> users = event.getHomeeUsers();
            for (HomeeUser user : users) {
                String notificationText = getNotificationText(event);
                emailService.sendEmail(
                        user.getEmail(),
                        "Notification for " + event.getName(),
                        notificationText
                );
                notificationService.addNewNotification(new NewNotificationDto(user.getId(), notificationText));
                log.info("Sent notification to " + user.getEmail() + " considering event " + event.getName());
            }
        }
    }

    private String getNotificationText(Event event) {
        return String.format("Dear Homee User, please remember about your custom event %s which is happening at %tA, %tb %td, %tY.",
                event.getName(),
                event.getScheduledAt(),
                event.getScheduledAt(),
                event.getScheduledAt(),
                event.getScheduledAt());
    }
}

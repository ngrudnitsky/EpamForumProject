package by.ngrudnitsky.service;

import by.ngrudnitsky.entity.NotificationEmail;

public interface MailService {

    void sendMail(NotificationEmail notificationEmail);
}

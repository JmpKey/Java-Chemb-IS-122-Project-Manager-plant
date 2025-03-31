package org.example.plant.protocol;

import javax.mail.Session;

public interface EMailCall {
    void connectMail(String fromEmail, String fromUserName, String fromPassword, String toEmail, String themeMail, String textMail);

    void sendEmail(Session session, String fromEmail, String fromUserName, String toEmail, String subject, String body);
}

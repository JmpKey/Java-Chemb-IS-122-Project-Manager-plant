package org.example.plant.protocol;

import org.example.plant.ProvinceMail;

import javax.mail.Session;

public interface EMailCall {
    void connectMail(String fromEmail, String fromUserName, String fromPassword, String toEmail, String themeMail, String textMail);

    void sendEmail(Session session, String fromEmail, String fromUserName, String toEmail, String subject, String body);

    void mailStart(String head, String mess);

    void setMetropolisController(Metropolis controller);
}

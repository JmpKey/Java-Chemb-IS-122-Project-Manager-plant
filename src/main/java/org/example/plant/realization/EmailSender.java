package org.example.plant.realization;

import org.example.plant.protocol.EMailCall;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class EmailSender implements EMailCall {
    private static EMailCall instance;

    public static EMailCall getInstance() {
        if (instance == null) {
            instance = new EmailSender();
        }
        return instance;
    }

    @Override
    public void connectMail(String fromEmail, String fromUserName, String fromPassword, String toEmail, String themeMail, String textMail) {
        ConfigReader configMail = ConfigReader.getInstance();
        List<String> configValues = configMail.readConfigValuesMail();

        if(Objects.equals(configValues.get(4), "on")) {
            Properties props = new Properties();
            props.put("mail.smtp.host", configValues.get(0)); //SMTP Host
            props.put("mail.smtp.port", configValues.get(1)); //TLS Port
            props.put("mail.smtp.auth", configValues.get(2)); //enable authentication
            props.put("mail.smtp.starttls.enable", configValues.get(3)); //enable STARTTLS

            //create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, fromPassword);
                }
            };
            Session session = Session.getInstance(props, auth);

            sendEmail(session, fromEmail, fromUserName, toEmail,themeMail, textMail);
        }
    }

    @Override
    public void sendEmail(Session session, String fromEmail, String fromUserName, String toEmail, String subject, String body) {
        try
        {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(fromEmail, fromUserName)); // user

            msg.setReplyTo(InternetAddress.parse(toEmail, false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

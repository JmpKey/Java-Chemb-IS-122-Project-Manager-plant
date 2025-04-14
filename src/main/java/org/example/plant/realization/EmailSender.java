package org.example.plant.realization;

import org.example.plant.ProvinceMail;
import org.example.plant.protocol.EMailCall;
import org.example.plant.protocol.Metropolis;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class EmailSender implements EMailCall {
    private Metropolis capitalWinController;
    private String file;

    private static EMailCall instance;

    public static EMailCall getInstance() {
        if (instance == null) {
            instance = new EmailSender();
        }
        return instance;
    }

    @Override
    public void setMetropolisController(Metropolis controller) { this.capitalWinController = controller; }

    @Override
    public void mailStart(String head, String mess) {
        JFileChooser fileChooser = new JFileChooser();

        // Открываем диалоговое окно для выбора файла
        int returnValue = fileChooser.showOpenDialog(null);

        // Проверяем, был ли выбран файл
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // Получаем выбранный файл
            File selectedFile = fileChooser.getSelectedFile();

            // Получаем путь к файлу
            file = selectedFile.getAbsolutePath();

            // Выводим путь к файлу
            System.out.println("Выбранный файл: " + file);
        } else {
            System.out.println("Файл не был выбран.");
        }

        connectMail(capitalWinController.getFromEmail(), capitalWinController.getApplication().getUsnameG(), capitalWinController.getEpass(), capitalWinController.getToEmail(), head, mess);
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
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(fromEmail, fromUserName)); // 2 nameFrom

            msg.setReplyTo(InternetAddress.parse(toEmail, false));

            msg.setSubject(subject, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setText(body);

            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Second part is attachment
            messageBodyPart = new MimeBodyPart();

            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(file);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            msg.setContent(multipart);

            // Send message
            Transport.send(msg);
            System.out.println("EMail Sent Successfully with attachment!!");
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

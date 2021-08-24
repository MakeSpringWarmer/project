package com.example.demo.service;

import com.example.demo.entity.SendMailRequest;
import org.springframework.stereotype.Service;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Service
public class MailService {

    public void sendMail(SendMailRequest request) {
        final String host = "smtp.gmail.com";
        final int port = 587;
        final boolean enableAuth = true;
        final boolean enableStarttls = true;
        final String userAddress = "broccoli8787@gmail.com";
        final String pwd = "avery8787";
        final String userDisplayName = "SchooLine";

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", String.valueOf(enableAuth));
        props.put("mail.smtp.starttls.enable", String.valueOf(enableStarttls));

        Session session = Session.getInstance(props, new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(userAddress, pwd);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setSubject(request.getSubject());
            message.setContent(request.getContent(), "text/html; charset=UTF-8");
            message.setFrom(new InternetAddress(userAddress, userDisplayName));
            String address = request.getReceivers();
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));


            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.ngash.portfolio.portfolio_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.to}")
    private String mailTo;

    @Value("${app.mail.from}")
    private String mailFrom;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendContactNotification(String firstName, String lastName, String email, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(mailTo);
        mail.setFrom(mailFrom);
        mail.setSubject("New Portfolio Contact: " + firstName + " " + lastName);

        String body = """
                You received a new message from your portfolio contact form.

                Name: %s %s
                Email: %s

                Message:
                %s
                """.formatted(firstName, lastName, email, message);

        mail.setText(body);

        mailSender.send(mail);
    }
}
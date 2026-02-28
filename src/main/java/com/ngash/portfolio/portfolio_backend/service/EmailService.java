package com.ngash.portfolio.portfolio_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class EmailService {

    @Value("${RESEND_API_KEY}")
    private String resendApiKey;

    @Value("${app.mail.to}")
    private String mailTo;

    @Value("${app.mail.from}")
    private String mailFrom;

    public void sendEmail(String name, String email, String message) {
        try {
            String jsonBody = """
                {
                  "from": "%s",
                  "to": ["%s"],
                  "subject": "New Contact Form Submission",
                  "html": "<strong>Name:</strong> %s<br/><strong>Email:</strong> %s<br/><strong>Message:</strong><br/>%s"
                }
                """.formatted(mailFrom, mailTo, name, email, message);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.resend.com/emails"))
                    .header("Authorization", "Bearer " + resendApiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                throw new RuntimeException("Failed to send email via Resend: " + response.body());
            }

        } catch (Exception e) {
            throw new RuntimeException("Email sending failed", e);
        }
    }
}
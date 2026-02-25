package com.ngash.portfolio.portfolio_backend.service;

import com.ngash.portfolio.portfolio_backend.dto.ContactRequest;
import com.ngash.portfolio.portfolio_backend.entity.ContactMessage;
import com.ngash.portfolio.portfolio_backend.repository.ContactMessageRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class ContactService {

    private final ContactMessageRepository repo;
    private final EmailService emailService;

    public ContactService(ContactMessageRepository repo, EmailService emailService) {
        this.repo = repo;
        this.emailService = emailService;
    }

    public Long submit(ContactRequest req) {
        // enforce consent (backend should also enforce)
        if (req.getDataConsent() == null || !req.getDataConsent()) {
            throw new IllegalArgumentException("Data consent is required.");
        }

        ContactMessage msg = new ContactMessage();
        msg.setFirstName(req.getFirstName());
        msg.setLastName(req.getLastName());
        msg.setEmail(req.getEmail());
        msg.setMessage(req.getMessage());
        msg.setMarketingConsent(req.isMarketingConsent());
        msg.setDataConsent(true);
        msg.setSubmittedAt(OffsetDateTime.now());

        //saving to db
        ContactMessage savedMsg = repo.save(msg);

        //trigger email notification
        try {
            emailService.sendContactNotification(
                    savedMsg.getFirstName(),
                    savedMsg.getLastName(),
                    savedMsg.getEmail(),
                    savedMsg.getMessage()
            );
        }catch (Exception ex){
            //logging the error
            System.out.println("Email send failed: " + ex.getMessage());
        }
        return savedMsg.getId();
    }
}
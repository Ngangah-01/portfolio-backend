package com.ngash.portfolio.portfolio_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Setter
@Getter
@Entity
@Table(name = "contact_messages")
public class ContactMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 1000)
    private String message;

    @Column(nullable = false)
    private boolean dataConsent;

    @Column(nullable = false)
    private boolean marketingConsent;

    @Column(nullable = false)
    private OffsetDateTime submittedAt;

}

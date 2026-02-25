package com.ngash.portfolio.portfolio_backend.controller;

import com.ngash.portfolio.portfolio_backend.dto.ContactRequest;
import com.ngash.portfolio.portfolio_backend.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> submitContact(@Valid @RequestBody ContactRequest request) {
            Long id = service.submit(request);
            return ResponseEntity.ok(Map.of(
                    "id", id,
                    "Status", "OK",
                    "Message", "Contact message submitted successfully."
            ));

    }
}

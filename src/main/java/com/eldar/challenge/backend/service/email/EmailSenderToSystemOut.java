package com.eldar.challenge.backend.service.email;

import org.springframework.stereotype.Service;

@Service
public class EmailSenderToSystemOut implements EmailSender {
    @Override
    public void send(String email, String subject, String message) {
        System.out.println("---------------------------------------------------");
        System.out.println("To: %s".formatted(email));
        System.out.println("Subject: %s".formatted(subject));
        System.out.println(message);
        System.out.println("---------------------------------------------------");
    }
}

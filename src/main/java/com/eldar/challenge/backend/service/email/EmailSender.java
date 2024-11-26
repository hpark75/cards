package com.eldar.challenge.backend.service.email;

public interface EmailSender {
    void send(final String email, final String subject, final String message);
}

package com.eldar.challenge.backend.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return new ResponseEntity<String>(mapper.writeValueAsString(new Message(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

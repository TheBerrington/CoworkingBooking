package ru.berrington.coworkingspace.util.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BadStartAndEndTimeException extends RuntimeException {

    public BadStartAndEndTimeException(String message) {
        super(message);
    }
}

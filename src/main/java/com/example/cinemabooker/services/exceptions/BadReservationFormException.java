package com.example.cinemabooker.services.exceptions;

public class BadReservationFormException extends RuntimeException {
    public BadReservationFormException(String message) {
        super(message);
    }
}

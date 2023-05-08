package com.example.cinemabooker.controllers.advices;

import com.example.cinemabooker.services.exceptions.BadReservationFormException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BadReservationFormAdvice {
    @ResponseBody
    @ExceptionHandler(BadReservationFormException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String badReservationFormHandler(BadReservationFormException e) {
        return e.getMessage();
    }
}

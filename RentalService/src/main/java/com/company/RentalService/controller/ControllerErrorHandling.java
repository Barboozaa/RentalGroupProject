package com.company.RentalService.controller;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerErrorHandling {
    @ExceptionHandler(value = {NullPointerException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<VndErrors> nullPointerHandler(NullPointerException e, WebRequest req) {
        VndErrors err = new VndErrors(req.toString(), e.getMessage());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.MULTIPLE_CHOICES)
    public ResponseEntity<VndErrors> badObjectHandler(IllegalArgumentException e, WebRequest req) {
        VndErrors err = new VndErrors(req.toString(), e.getMessage());
        return new ResponseEntity<>(err, HttpStatus.MULTIPLE_CHOICES);
    }
}

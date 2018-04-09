package com.cmpt213.a5.courseplanner.model.dataobjects;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class OfferingNotFoundException extends IllegalArgumentException {
    public OfferingNotFoundException(String message) {
        super(message);
    }
}

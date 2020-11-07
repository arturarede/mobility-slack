package com.mobility.configuration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MobilityNotFoundException extends MobilityException {
    private static final long serialVersionUID = 1L;

    public MobilityNotFoundException(final String message) {
        super(message);
    }
}

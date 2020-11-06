package com.mobility.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MobilityBadRequestException extends MobilityException {
    private static final long serialVersionUID = 1L;

    public MobilityBadRequestException(final String message, final Exception e) {
        super(message, e);
    }

    public MobilityBadRequestException(final String message) {
        super(message);
    }
}

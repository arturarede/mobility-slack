package com.mobility.configuration.exceptions;

public abstract class MobilityException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    protected MobilityException(final String message) {
        super(message);
    }
}

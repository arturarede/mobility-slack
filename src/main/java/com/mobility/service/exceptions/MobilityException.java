package com.mobility.service.exceptions;

public abstract class MobilityException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public MobilityException() { }

    public MobilityException(final String message, final Throwable e) {
        super(message, e);
    }

    public MobilityException(final String message) {
        super(message);
    }
}

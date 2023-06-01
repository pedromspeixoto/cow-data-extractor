package com.cde.utils.exceptions;

public class NotFoundException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MESSAGE_PREFIX_MODEL = "the resource was not found. requested identifier: ";

    public NotFoundException(String identifier) {
        super(DEFAULT_MESSAGE_PREFIX_MODEL + identifier);
    }

    public NotFoundException(String identifier, Throwable cause) {
        super(DEFAULT_MESSAGE_PREFIX_MODEL + identifier, cause);
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
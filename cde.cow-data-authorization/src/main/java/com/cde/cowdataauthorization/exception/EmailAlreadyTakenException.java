package com.cde.cowdataauthorization.exception;

public class EmailAlreadyTakenException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MESSAGE_PREFIX_MODEL = "email already taken. requested email: ";

    public EmailAlreadyTakenException(String identifier) {
        super(DEFAULT_MESSAGE_PREFIX_MODEL + identifier);
    }

    public EmailAlreadyTakenException(String identifier, Throwable cause) {
        super(DEFAULT_MESSAGE_PREFIX_MODEL + identifier, cause);
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
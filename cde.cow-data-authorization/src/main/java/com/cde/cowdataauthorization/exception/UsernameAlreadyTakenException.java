package com.cde.cowdataauthorization.exception;

public class UsernameAlreadyTakenException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MESSAGE_PREFIX_MODEL = "username already taken. requested username: ";

    public UsernameAlreadyTakenException(String identifier) {
        super(DEFAULT_MESSAGE_PREFIX_MODEL + identifier);
    }

    public UsernameAlreadyTakenException(String identifier, Throwable cause) {
        super(DEFAULT_MESSAGE_PREFIX_MODEL + identifier, cause);
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
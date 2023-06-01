package com.cde.utils.exceptions;

public class NoDataFoundException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MESSAGE_PREFIX_MODEL = "no data found. requested identifier: ";

    public NoDataFoundException(String identifier) {
        super(DEFAULT_MESSAGE_PREFIX_MODEL + identifier);
    }

    public NoDataFoundException(String identifier, Throwable cause) {
        super(DEFAULT_MESSAGE_PREFIX_MODEL + identifier, cause);
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
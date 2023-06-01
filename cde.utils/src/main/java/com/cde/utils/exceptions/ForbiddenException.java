package com.cde.utils.exceptions;

public class ForbiddenException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MESSAGE_PREFIX_MODEL = "user does not have access to this resource";

    public ForbiddenException() {
        super(DEFAULT_MESSAGE_PREFIX_MODEL);
    }

    public ForbiddenException(Throwable cause) {
        super(DEFAULT_MESSAGE_PREFIX_MODEL, cause);
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
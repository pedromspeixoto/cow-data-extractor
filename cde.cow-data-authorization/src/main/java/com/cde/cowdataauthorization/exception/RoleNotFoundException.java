package com.cde.cowdataauthorization.exception;

public class RoleNotFoundException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MESSAGE_PREFIX_MODEL = "role not found. requested role: ";

    public RoleNotFoundException(String identifier) {
        super(DEFAULT_MESSAGE_PREFIX_MODEL + identifier);
    }

    public RoleNotFoundException(String identifier, Throwable cause) {
        super(DEFAULT_MESSAGE_PREFIX_MODEL + identifier, cause);
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
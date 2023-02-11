package com.attornatus.project.exceptions;

import java.util.NoSuchElementException;

public class ObjectNotFoundException extends NoSuchElementException {
    public ObjectNotFoundException() {
        super();
    }

    public ObjectNotFoundException(String s) {
        super(s);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return super.fillInStackTrace();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

package com.attornatus.project.exceptions;

import org.hibernate.PropertyValueException;

public class InvalidPropertyException extends PropertyValueException {
    /**
     * Constructs a PropertyValueException using the specified information.
     *
     * @param message      A message explaining the exception condition
     * @param entityName   The name of the entity, containing the property
     * @param propertyName The name of the property being accessed.
     */
    public InvalidPropertyException(String message, String entityName, String propertyName) {
        super(message, entityName, propertyName);
    }
}

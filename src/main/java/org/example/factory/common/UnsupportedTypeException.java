package org.example.factory.common;

/**
 * Thrown by a factory when asked for a type it has no implementation for.
 */
public class UnsupportedTypeException extends RuntimeException {

    public UnsupportedTypeException(String message) {
        super(message);
    }
}

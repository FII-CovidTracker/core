package com.example.demo.Exceptions;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(Long id, String argument) {
        super(String.format("The argument %s with the value %s is not VALID", argument, id));
    }
}

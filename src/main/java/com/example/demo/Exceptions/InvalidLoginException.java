package com.example.demo.Exceptions;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException() {
        super("Invalid login parameters!");
    }
}

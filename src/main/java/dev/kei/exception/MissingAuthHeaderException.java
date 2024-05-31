package dev.kei.exception;

public class MissingAuthHeaderException extends RuntimeException{
    public MissingAuthHeaderException(String error) {
        super(error);
    }
}

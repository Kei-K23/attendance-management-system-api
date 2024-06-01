package dev.kei.exception;

public class AlreadyMakeCheckInException extends RuntimeException{
    public AlreadyMakeCheckInException(String error) {
        super(error);
    }
}

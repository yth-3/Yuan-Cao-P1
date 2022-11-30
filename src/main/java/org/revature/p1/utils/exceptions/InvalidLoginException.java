package org.revature.p1.utils.exceptions;

public class InvalidLoginException extends RuntimeException{
    public InvalidLoginException(String ErrorMessage) {
        super(ErrorMessage);
    }
}

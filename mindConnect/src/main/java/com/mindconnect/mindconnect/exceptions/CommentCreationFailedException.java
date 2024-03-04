package com.mindconnect.mindconnect.exceptions;

public class CommentCreationFailedException extends RuntimeException{
    public CommentCreationFailedException(String message){
        super(message);
    }
}

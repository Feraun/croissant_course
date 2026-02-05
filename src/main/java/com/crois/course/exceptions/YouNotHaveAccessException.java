package com.crois.course.exceptions;

public class YouNotHaveAccessException extends RuntimeException {
    public YouNotHaveAccessException(String message) {
        super(message);
    }
}

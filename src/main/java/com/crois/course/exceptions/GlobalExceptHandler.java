package com.crois.course.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptHandler {

    @ExceptionHandler
    public ResponseEntity<AppError> catchGroupNotFoundException(NotFoundException e){
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(
                new AppError(HttpStatus.NOT_FOUND.value(),
                e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchYouNotHaveAccessException(YouNotHaveAccessException e){
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(
                new AppError(HttpStatus.FORBIDDEN.value(),
                e.getMessage()),
                HttpStatus.FORBIDDEN);
    }
}

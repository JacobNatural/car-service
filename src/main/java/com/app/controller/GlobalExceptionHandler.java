package com.app.controller;

import com.app.controller.dto.ResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler that handles various types of exceptions and returns appropriate HTTP responses.
 * The handler catches exceptions thrown within controllers and returns well-formed responses with specific HTTP statuses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles IllegalArgumentException thrown in the application.
     *
     * @param ex The exception thrown.
     * @return A ResponseDto containing the error message.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto<String> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        return new ResponseDto<>(ex.getMessage());
    }

    /**
     * Handles EntityNotFoundException thrown when an entity is not found.
     *
     * @param ex The exception thrown.
     * @return A ResponseDto containing the error message.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseDto<String> entityNotFoundExceptionHandler(EntityNotFoundException ex) {
        return new ResponseDto<>(ex.getMessage());
    }

    /**
     * Handles DataIntegrityViolationException thrown when a data integrity violation occurs,
     * such as when an entry with a duplicate key is found in the database.
     *
     * @param ex The exception thrown.
     * @return A ResponseDto containing a specific error message.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseDto<String> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException ex) {
        return new ResponseDto<>("An entry with the given key already exists in the database.");
    }

    /**
     * Handles all other unexpected exceptions.
     *
     * @param e The exception thrown.
     * @return A ResponseDto containing the exception message.
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDto<String> handleUnexpectedExceptions(Exception e) {
        return new ResponseDto<>(e.getMessage());
    }
}

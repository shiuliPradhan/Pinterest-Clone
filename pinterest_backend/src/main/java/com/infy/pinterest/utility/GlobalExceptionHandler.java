package com.infy.pinterest.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.infy.pinterest.exception.InfyPinterestException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InfyPinterestException.class)
    public ResponseEntity<ErrorInfo> handleBusinessException(
            InfyPinterestException ex, WebRequest request
    ) {
        ErrorInfo info = new ErrorInfo(
                ex.getMessage(),
                "BUSINESS_ERROR",
                System.currentTimeMillis(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> handleGeneralException(
            Exception ex, WebRequest request
    ) {
        ErrorInfo info = new ErrorInfo(
                ex.getMessage(),
                "SERVER_ERROR",
                System.currentTimeMillis(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


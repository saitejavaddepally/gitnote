package com.example.patterns.exceptions;

import com.example.patterns.service.imp.ArticleApproveService;
import com.example.patterns.vo.auth.ErrorResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ArticleApproveService.class);

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse("Unauthorized", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse("Not Found", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse("Bad Request", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public final ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse("Not Found", "The requested resource was not found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error("Something went wrong ! {}", ExceptionUtils.getStackTrace(ex));
        ErrorResponse error = new ErrorResponse("Internal Server Error", ex.fillInStackTrace().getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.sapient.football.league.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.sapient.football.league.model.ErrorResponse;
import com.sapient.football.league.util.Constants;
import com.sapient.football.league.util.exception.BadRequestException;
import com.sapient.football.league.util.exception.NotFoundException;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@ControllerAdvice
public class ErrorAdvice extends ResponseEntityExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(ErrorAdvice.class);

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception exception, WebRequest request) {
		logger.error("Bad Request",  exception);
        List<String> details = Lists.newArrayList(Optional.fromNullable(exception.getMessage()).or(exception.getClass().getSimpleName()));
        ErrorResponse error = new ErrorResponse(Constants.BAD_REQUEST, details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception exception, WebRequest request) {
    	logger.error("Not Found Exception",  exception);
        List<String> details = Lists.newArrayList(Optional.fromNullable(exception.getMessage()).or(exception.getClass().getSimpleName()));
        ErrorResponse error = new ErrorResponse(Constants.NOT_FOUND, details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptions(Exception exception, WebRequest request) {
    	logger.error("Exception",  exception);
        List<String> details = Lists.newArrayList(Optional.fromNullable(exception.getMessage()).or(exception.getClass().getSimpleName()));
        ErrorResponse error = new ErrorResponse(Constants.ERROR, details);
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }
}

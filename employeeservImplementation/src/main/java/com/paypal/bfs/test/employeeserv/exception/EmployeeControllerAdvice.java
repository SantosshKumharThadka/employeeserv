package com.paypal.bfs.test.employeeserv.exception;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class EmployeeControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleEmployeeNotFoundException(EmployeeNotFoundException ex,
	    WebRequest request) {
	ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
	return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeAlreadyExistsException.class)
    public final ResponseEntity<ErrorDetails> handleEmployeeAlreadyExistsException(EmployeeAlreadyExistsException ex,
	    WebRequest request) {
	ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
	return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(DateFormatIncorrectException.class)
    public final ResponseEntity<ErrorDetails> handleDateFormatIncorrectException(DateFormatIncorrectException ex,
	    WebRequest request) {
	ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
	return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ErrorDetails> handleConstraintViolationException(ConstraintViolationException e,
	    WebRequest request) {
	ErrorDetails errorDetails = new ErrorDetails(new Date(), "not valid due to validation error: " + e.getMessage(),
		request.getDescription(false));
	return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() +" " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        System.out.println("Error List"+errorList);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), errorList.toString(), request.getDescription(false));
        return handleExceptionInternal(ex, errorDetails, headers, HttpStatus.BAD_REQUEST, request);
    }

}

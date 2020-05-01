package com.evolent.contactmgmt.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.evolent.contactmgmt.model.ErrorMessage;

@RestControllerAdvice
public class ExceptionControllerAdvice {
	@Autowired
	Environment env;
	
	@ExceptionHandler(Exception.class)
	public String  exceptionHandler(Exception ex) {
		return  ex.getMessage();
	}
	
	@ExceptionHandler(NoSuchContactException.class)
	public ResponseEntity<ErrorMessage> exceptionHandler(NoSuchContactException ex) {
		 ErrorMessage error = new ErrorMessage();
	        error.setErrorCode(HttpStatus.NOT_FOUND.value());
	        error.setMessage(env.getProperty(ex.getMessage()));
	        return new ResponseEntity<>(error, HttpStatus.OK);
		 
	}
	@ExceptionHandler(DuplicateContactException.class)
	public ResponseEntity<ErrorMessage> exceptionHandler2(DuplicateContactException ex) {
		 ErrorMessage error = new ErrorMessage();
	        error.setErrorCode(HttpStatus.CONFLICT.value());
	        error.setMessage(env.getProperty(ex.getMessage()));
	        return new ResponseEntity<>(error, HttpStatus.OK);
	}
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ErrorMessage> responseStatus(ResponseStatusException ex) {
		 ErrorMessage error = new ErrorMessage();
	        error.setErrorCode(HttpStatus.NOT_FOUND.value());
	        error.setMessage(env.getProperty(ex.getMessage()));
	        return new ResponseEntity<>(error, HttpStatus.OK);
		 
	}
	 
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
	
	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<ErrorMessage> handleRequest(InvalidRequestException ex) {
		 ErrorMessage error = new ErrorMessage();
	        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
	        error.setMessage(env.getProperty(ex.getMessage()));
	        return new ResponseEntity<>(error, HttpStatus.OK);
	}
}

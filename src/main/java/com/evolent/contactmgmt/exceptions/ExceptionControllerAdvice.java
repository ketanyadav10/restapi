package com.evolent.contactmgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.evolent.contactmgmt.model.ErrorMessage;

@RestControllerAdvice
public class ExceptionControllerAdvice {
	@ExceptionHandler(Exception.class)
	public String  exceptionHandler(Exception ex) {
		return  ex.getMessage();
	}
	
	@ExceptionHandler(NoSuchContactException.class)
	public ResponseEntity<ErrorMessage> exceptionHandler2(NoSuchContactException ex) {
		 ErrorMessage error = new ErrorMessage();
	        error.setErrorCode(HttpStatus.BAD_GATEWAY.value());
	        error.setMessage(ex.getMessage());
	        return new ResponseEntity<>(error, HttpStatus.OK);
		 
	}
	@ExceptionHandler(DuplicateContactException.class)
	public ResponseEntity<ErrorMessage> exceptionHandler2(DuplicateContactException ex) {
		 ErrorMessage error = new ErrorMessage();
	        error.setErrorCode(HttpStatus.BAD_GATEWAY.value());
	        error.setMessage(ex.getMessage());
	        return new ResponseEntity<>(error, HttpStatus.OK);
		 
	}
}
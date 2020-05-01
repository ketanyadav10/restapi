package com.evolent.contactmgmt.exceptions;

public class InvalidRequestException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public InvalidRequestException() {
		super();
	}
	public InvalidRequestException(String msg) {
		super(msg);
	}
}

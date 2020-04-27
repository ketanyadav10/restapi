package com.evolent.contactmgmt.exceptions;

public class DuplicateContactException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public DuplicateContactException() {
		super();
	}
	public DuplicateContactException(String msg){
		super(msg);
	}
}

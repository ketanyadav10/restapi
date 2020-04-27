package com.evolent.contactmgmt.exceptions;

public class NoSuchContactException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public NoSuchContactException() {
		super();
	}
	public NoSuchContactException(String msg) {
		super(msg);
	}

}

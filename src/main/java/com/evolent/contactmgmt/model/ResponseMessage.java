package com.evolent.contactmgmt.model;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseMessage {
	@JsonProperty("response_code")
	private HttpStatus responseCode;
	@JsonProperty("message")
	String message;
	public ResponseMessage() {
		this.responseCode=HttpStatus.OK;
		this.message="";
	}
	public HttpStatus getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(HttpStatus responseCode) {
		this.responseCode = responseCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ResponseMessage(HttpStatus responseCode, String message) {
		super();
		this.responseCode = responseCode;
		this.message = message;
	}
	
}

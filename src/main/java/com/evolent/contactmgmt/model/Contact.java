package com.evolent.contactmgmt.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contact {
	@NotBlank
	@JsonProperty("first_name")
	private String firstName;
	@NotBlank
	@JsonProperty("last_name")
	private String lastName;
	@JsonProperty("email_id")
	@Email(message="Email id is not in a valid format, please check.")
	private String emailId;
	@NotBlank(message="Phone Number is mandatory.")
	@JsonProperty("phone_no")
	@Pattern(regexp="([0-9]{10})",message="Phone no must be of 10 digits.")
	private String phoneNo;
	@Pattern(regexp="[A|a]ctive|[I|i]nactive|[A|a]CTIVE|[A|a]NACTIVE",message="Status can be either Active or Inactive.")
	@NotBlank(message="status cannot be empty")
	@JsonProperty("status")
	private String status;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}

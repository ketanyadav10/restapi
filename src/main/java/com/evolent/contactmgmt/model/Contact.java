package com.evolent.contactmgmt.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.lang.NonNull;

public class Contact {
	private String firstName;
	private String lastName;
	@Email(message="Email id is not in a valid format, please check.")
	private String emailId;
	@NotBlank(message="Phone Number is mandatory.")
	@Pattern(regexp="(^$|[0-9]{10})",message="Phone no must be of 10 digits.")
	private String phoneNo;
	@Pattern(regexp="[A|a]ctive|[I|i]nactive|ACTIVE|INACTIVE",message="Status can be either Active or Inactive.")
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

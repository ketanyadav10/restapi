package com.evolent.contactmgmt.dao;

import java.util.List;

import com.evolent.contactmgmt.model.Contact;

public interface ContactDAO {
	public String addContact(Contact contact) throws Exception;
	public Contact getContact(String phoneNo) throws Exception;
	public void updateContact(String phoneNo, Contact contact) throws Exception;
	public void replaceContact(String phoneNo, Contact contact) throws Exception;
	public void deleteContact(String phoneNo) throws Exception;
	public List<Contact> getAllContactDetails() throws Exception;
}

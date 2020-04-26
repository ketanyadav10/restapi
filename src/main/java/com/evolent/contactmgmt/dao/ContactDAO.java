package com.evolent.contactmgmt.dao;

import java.util.List;

import com.evolent.contactmgmt.model.Contact;

public interface ContactDAO {
	public Integer addContact(Contact contact) throws Exception;
	public Contact getContact(Integer contactId) throws Exception;
	public void updateContact(Integer contactId, String emailId) throws Exception;
	public void deleteContact(Integer contactId) throws Exception;
	public List<Contact> getAllContactDetails() throws Exception;
}

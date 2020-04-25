package com.evolent.contactmgmt.service;

import java.util.List;
import com.evolent.contactmgmt.model.Contact;

public interface ContactService {
	public void addContact(Contact contact) throws Exception;
	public Contact getContact(Integer contactId) throws Exception;
	public void updateContact(Integer contactId, String emailId) throws Exception;
	public void deleteContact(Integer contactId) throws Exception;
	public List<Contact> getAllContactDetails() throws Exception;
}

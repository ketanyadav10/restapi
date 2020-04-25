package com.evolent.contactmgmt.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.evolent.contactmgmt.model.Contact;
@Repository(value="contactDAO")
public class ContactDAOImpl implements ContactDAO{
	
	private Map<Integer, Contact> contacts = new HashMap<Integer, Contact>();
	public ContactDAOImpl() {
		contacts.put(1, new Contact(1, "Ketan", "Yadav", "kyadav073@gmail.com", "+917387431338", true));
		contacts.put(2, new Contact(2, "Toran", "Sahu", "toransahu@gmail.com", "+918602431733", true));

	}
	@Override
	public void addContact(Contact contact) throws Exception {
		contacts.put(contact.getContactId(), contact);
	}
	@Override
	public Contact getContact(Integer contactId) throws Exception {
		return contacts.get(contactId);
	}
	@Override
	public void updateContact(Integer contactId, String emailId) throws Exception {
		contacts.get(contactId).setEmailId(emailId);
	}
	@Override
	public void deleteContact(Integer contactId) throws Exception {
		contacts.remove(contactId);
		
	}
	@Override
	public List<Contact> getAllContactDetails() throws Exception {
		return new ArrayList<>(contacts.values());
	}
}

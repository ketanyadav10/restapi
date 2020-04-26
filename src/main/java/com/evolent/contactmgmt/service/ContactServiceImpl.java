package com.evolent.contactmgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evolent.contactmgmt.dao.ContactDAO;
import com.evolent.contactmgmt.model.Contact;

@Service(value="contactService")
@Transactional
public class ContactServiceImpl implements ContactService {
	@Autowired
	ContactDAO contactDAO;
	@Override
	public void addContact(Contact contact) throws Exception {
		if(contactDAO.getContact(contact.getPhoneNo())!=null) {
			throw new Exception("Service.CONTACT_ALREADY_EXISTS");
		}
		contactDAO.addContact(contact);
	}

	@Override
	public Contact getContact(String phoneNo) throws Exception {
		Contact contact = contactDAO.getContact(phoneNo);
		if (contact == null) {
			throw new Exception("Service.CONTACT_UNAVAILABLE");
		}
		return contact;
	}

	

	@Override
	public void deleteContact(String phoneNo) throws Exception {
		Contact contact = contactDAO.getContact(phoneNo);
		if (contact == null) {
			throw new Exception("Service.CONTACT_UNAVAILABLE");
		}
		contactDAO.deleteContact(phoneNo);
	}

	@Override
	public List<Contact> getAllContactDetails() throws Exception {
		List<Contact> contactList = contactDAO.getAllContactDetails();
		if (contactList == null) {
			throw new Exception("Service.NO_CONTACT_AVAILABLE");
		}
		return contactList;
	}

	@Override
	public void updateContact(String phoneNo, Contact contact) throws Exception {

		Contact existing = contactDAO.getContact(phoneNo);
		if (existing == null) {
			throw new Exception("Service.CONTACT_UNAVAILABLE");
		}		
		contactDAO.updateContact(phoneNo, contact);
	
	}

	@Override
	public void createAndUpdateContact(String phoneNo, Contact contact) throws Exception {
		Contact existing = contactDAO.getContact(phoneNo);
		if (existing == null) 
			addContact(contact);
		else
			contactDAO.replaceContact(phoneNo, contact);
	}
}

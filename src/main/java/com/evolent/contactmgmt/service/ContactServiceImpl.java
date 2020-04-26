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
		if(contactDAO.getContact(contact.getContactId())!=null) {
			throw new Exception("Service.CONTACT_ALREADY_EXISTS");
		}
		contactDAO.addContact(contact);
	}

	@Override
	public Contact getContact(Integer contactId) throws Exception {


		Contact contact = contactDAO.getContact(contactId);
		if (contact == null) {
			throw new Exception("Service.CONTACT_UNAVAILABLE");
		}
		return contact;
	
	}

	@Override
	public void updateContact(Integer contactId, String emailId) throws Exception {

		Contact contact = contactDAO.getContact(contactId);
		if (contact == null) {
			throw new Exception("Service.CONTACT_UNAVAILABLE");
		}		
		contactDAO.updateContact(contactId, emailId);
	
	}

	@Override
	public void deleteContact(Integer contactId) throws Exception {
		Contact contact = contactDAO.getContact(contactId);
		if (contact == null) {
			throw new Exception("Service.CONTACT_UNAVAILABLE");
		}
		contactDAO.deleteContact(contactId);
	}

	@Override
	public List<Contact> getAllContactDetails() throws Exception {
		List<Contact> contactList = contactDAO.getAllContactDetails();
		if (contactList == null) {
			throw new Exception("Service.NO_CONTACT_AVAILABLE");
		}
		return contactList;
	}
}
